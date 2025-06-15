import { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/Form.css';

export default function ProfessorDashboard() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    studentName: '',
    amount: '',
    message: ''
  });
  const [transactions, setTransactions] = useState([]);
  const [balance, setBalance] = useState(0);

  const user = JSON.parse(localStorage.getItem('user'));
  const teacherId = user?.id;

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleTransfer = async (e) => {
    e.preventDefault();

    const payload = {
      teacherId,
      studentName: formData.studentName,
      amount: Number(formData.amount),
      message: formData.message
    };

    try {
      await axios.post('http://localhost:8080/api/transactions/transfer', payload); // novo endpoint aqui
      alert('Moedas transferidas com sucesso!');
      setFormData({ studentName: '', amount: '', message: '' });
      fetchStatement(); // recarrega extrato após transferência
    } catch (error) {
      console.error('Erro ao transferir moedas:', error);
      alert('Erro ao transferir moedas.');
    }
  };

  const fetchStatement = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/teachers/statement`, {
        params: { teacherId }
      });
      setTransactions(response.data.sentTransactions);
      setBalance(response.data.balance);
    } catch (error) {
      console.error('Erro ao buscar extrato:', error);
      alert('Erro ao carregar extrato.');
    }
  };

  useEffect(() => {
    if (!teacherId) {
      alert('Sessão inválida. Faça login novamente.');
      navigate('/');
    } else {
      fetchStatement();
    }
  }, []);

  const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

  return (
    <div className="form-container">
      <h2>Bem-vindo, Professor(a) {user?.name}</h2>
      <p><strong>Saldo atual:</strong> {balance} moedas</p>

      <h3>Transferir moedas para aluno</h3>
      <form onSubmit={handleTransfer}>
        <input
          name="studentName"
          placeholder="Nome do Aluno"
          value={formData.studentName}
          onChange={handleChange}
          required
        />
        <input
          name="amount"
          placeholder="Quantidade de moedas"
          type="number"
          value={formData.amount}
          onChange={handleChange}
          required
        />
        <input
          name="message"
          placeholder="Mensagem (opcional)"
          value={formData.message}
          onChange={handleChange}
        />
        <button type="submit">Transferir</button>
      </form>

      <h3>Histórico de transferências</h3>
      {transactions.length === 0 ? (
        <p>Nenhuma transferência realizada ainda.</p>
      ) : (
        <ul>
          {transactions.map((tx, index) => (
            <li key={index}>
              <strong>Aluno:</strong> {tx.studentName || tx.recipientName}<br />
              <strong>Valor:</strong> {tx.amount} moedas<br />
              <strong>Mensagem:</strong> {tx.message}<br />
              <hr />
            </li>
          ))}
        </ul>
      )}

      <button onClick={handleLogout} className="secondary-button" style={{ marginTop: '20px' }}>
        Sair
      </button>
    </div>
  );
}

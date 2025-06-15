import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/Form.css';

export default function StudentDashboard() {
  const navigate = useNavigate();
  const [balance, setBalance] = useState(0);
  const [receivedTransactions, setReceivedTransactions] = useState([]);
  const [redeemedRewards, setRedeemedRewards] = useState([]);
  const [availableRewards, setAvailableRewards] = useState([]);

  const user = JSON.parse(localStorage.getItem('user'));
  const studentId = user?.id;

  const fetchStatement = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/students/statement/${studentId}`);
      setBalance(response.data.balance);
      setReceivedTransactions(response.data.receivedTransactions);
    } catch (error) {
      console.error('Erro ao buscar extrato:', error);
      alert('Erro ao carregar extrato.');
    }
  };

  const fetchRedeemedRewards = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/rewards/redeem/student/${studentId}`);
      setRedeemedRewards(response.data);
    } catch (error) {
      console.error('Erro ao buscar recompensas resgatadas:', error);
    }
  };

  const fetchAvailableRewards = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/rewards/all');
      setAvailableRewards(response.data);
    } catch (error) {
      console.error('Erro ao buscar recompensas:', error);
    }
  };

  const handleRedeem = async (rewardId) => {
    try {
      const response = await axios.post(`http://localhost:8080/api/students/redeem/${studentId}/${rewardId}`);
      alert(`Seu código de resgate é: ${response.data.redemptionCode}`);
      fetchStatement();
      fetchRedeemedRewards();
    } catch (error) {
      console.error('Erro ao resgatar recompensa:', error);
      alert('Erro ao resgatar recompensa.');
    }
  };

  useEffect(() => {
    if (!studentId) {
      alert('Sessão inválida. Faça login novamente.');
      navigate('/');
    } else {
      fetchStatement();
      fetchAvailableRewards();
      fetchRedeemedRewards();
    }
  }, []);

  const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

  return (
    <div className="form-container">
      <h2>Bem-vindo, {user?.name}</h2>
      <p><strong>Saldo atual:</strong> {balance} moedas</p>

      <h3>Recompensas Disponíveis</h3>
      {availableRewards.length === 0 ? (
        <p>Nenhuma recompensa disponível no momento.</p>
      ) : (
        <ul>
          {availableRewards.map((reward) => (
            <li key={reward.id}>
              <strong>{reward.title}</strong> ({reward.cost} moedas)<br />
              <em>{reward.description}</em><br />
              Empresa: {reward.companyName}<br />
              <button onClick={() => handleRedeem(reward.id)}>Resgatar</button>
              <hr />
            </li>
          ))}
        </ul>
      )}

      <h3>Histórico de Transações Recebidas</h3>
      {receivedTransactions.length === 0 ? <p>Nenhuma moeda recebida ainda.</p> : (
        <ul>
          {receivedTransactions.map((tx, index) => (
            <li key={index}>
              <strong>Professor:</strong> {tx.teacherName}<br />
              <strong>Quantidade:</strong> {tx.amount} moedas<br />
              <strong>Mensagem:</strong> {tx.message}<br />
              <strong>Data:</strong> {tx.date}<br />
              <hr />
            </li>
          ))}
        </ul>
      )}

      <h3>Recompensas Resgatadas</h3>
      {redeemedRewards.length === 0 ? <p>Nenhuma recompensa resgatada ainda.</p> : (
        <ul>
          {redeemedRewards.map((reward, index) => (
            <li key={index}>
              <strong>{reward.rewardTitle}</strong><br />
              Empresa: {reward.companyName}<br />
              Código: {reward.code}<br />
              Data: {new Date(reward.redeemedAt).toLocaleDateString()}<br />
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

import { useEffect, useState } from 'react';
import axios from 'axios';
import '../styles/Form.css';

export default function CompanyDashboard() {
  const [rewards, setRewards] = useState([]);
  const [newReward, setNewReward] = useState({
    title: '',
    description: '',
    cost: ''
  });

  const company = JSON.parse(localStorage.getItem('user'));

  const loadRewards = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/rewards/company/${company.id}`);
      setRewards(response.data);
    } catch (error) {
      console.error('Erro ao carregar vantagens:', error);
    }
  };

  useEffect(() => {
    loadRewards();
  }, []);

  const handleChange = (e) => {
    setNewReward({ ...newReward, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      ...newReward,
      cost: parseInt(newReward.cost),
      partnerCompanyId: company.id
    };

    try {
      await axios.post('http://localhost:8080/api/rewards/register', payload);
      alert('Vantagem cadastrada com sucesso!');
      setNewReward({ title: '', description: '', cost: '' });
      loadRewards();
    } catch (error) {
      console.error('Erro ao cadastrar vantagem:', error);
      alert('Erro ao cadastrar vantagem.');
    }
  };

  return (
    <div className="form-container">
      <h2>Bem-vindo, {company.name}</h2>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h2>Bem-vindo, {company.name}</h2>
        <button
          style={{ backgroundColor: '#f44336', color: '#fff', border: 'none', padding: '8px 12px', borderRadius: '5px' }}
          onClick={() => {
            localStorage.clear();
            window.location.href = '/';
          }}
        >
          Sair
        </button>
      </div>
      <p style={{ marginBottom: '20px' }}>Email: {company.email}</p>

      <h3>Cadastrar Nova Vantagem</h3>
      <form onSubmit={handleSubmit}>
        <input
          name="title"
          placeholder="Título da Vantagem"
          onChange={handleChange}
          value={newReward.title}
          required
        />
        <input
          name="description"
          placeholder="Descrição"
          onChange={handleChange}
          value={newReward.description}
          required
        />
        <input
          name="cost"
          type="number"
          placeholder="Custo em moedas"
          onChange={handleChange}
          value={newReward.cost}
          required
        />
        <button type="submit">Cadastrar</button>
      </form>

      <h3 style={{ marginTop: '40px' }}>Vantagens Cadastradas</h3>
      {rewards.length === 0 ? (
        <p>Nenhuma vantagem cadastrada ainda.</p>
      ) : (
        <ul style={{ paddingLeft: '20px' }}>
          {rewards.map((reward) => (
            <li key={reward.id} style={{ marginBottom: '10px' }}>
              <strong>{reward.title}</strong> — {reward.description}
              <br />
              🎯 Custo: {reward.cost} moedas
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

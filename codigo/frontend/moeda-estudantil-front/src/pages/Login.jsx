import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/Form.css'; // 👈 importar o CSS compartilhado

export default function Login() {
  const navigate = useNavigate();
  const [role, setRole] = useState('student');
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    let endpoint = '';
    
    if (role === 'student') {
      endpoint = 'http://localhost:8080/api/students/login';
    } else if (role === 'teacher') {
      endpoint = 'http://localhost:8080/api/teachers/login';
    } else if (role === 'company') {
      endpoint = 'http://localhost:8080/api/companies/login';
    }

    try {
      const response = await axios.post(endpoint, formData);
      const userData = response.data;

      localStorage.setItem('user', JSON.stringify(userData));
      localStorage.setItem('role', role);

      if (role === 'student') {
        navigate('/dashboard-aluno');
      } else if (role === 'teacher') {
        navigate('/dashboard-professor');
      } else {
        navigate('/dashboard-empresa');
      }
    } catch (error) {
      console.error('Erro ao fazer login:', error);
      alert('Credenciais inválidas ou erro no servidor.');
    }
  };

  return (
    <div className="form-container">
      <h2>Login</h2>

      <select value={role} onChange={(e) => setRole(e.target.value)}>
        <option value="student">Aluno</option>
        <option value="teacher">Professor</option>
        <option value="company">Empresa</option>
      </select>

      <form onSubmit={handleLogin}>
        <input
          type="email"
          name="email"
          placeholder="Email"
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Senha"
          onChange={handleChange}
          required
        />

        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <button type="submit">Entrar</button>
          <button
            type="button"
            className="secondary-button"
            onClick={() => navigate('/cadastro')}
          >
            Cadastre-se
          </button>
        </div>
      </form>
    </div>
  );
}

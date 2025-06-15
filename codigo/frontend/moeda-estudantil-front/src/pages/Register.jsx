import { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/Form.css';

export default function Register() {
  const navigate = useNavigate();
  const [role, setRole] = useState('student');
  const [institutions, setInstitutions] = useState([]);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    cpf: '',
    rg: '',
    address: '',
    course: '',
    department: '',
    password: '',
    institutionId: ''
  });

  useEffect(() => {
    async function fetchInstitutions() {
      try {
        const response = await axios.get('http://localhost:8080/api/institutions');
        setInstitutions(response.data);
      } catch (error) {
        console.error('Erro ao buscar instituições:', error);
        alert('Erro ao carregar instituições.');
      }
    }

    fetchInstitutions();
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (role === 'student') {
        const payload = {
          name: formData.name,
          email: formData.email,
          cpf: formData.cpf,
          rg: formData.rg,
          address: formData.address,
          course: formData.course,
          password: formData.password,
          institutionId: Number(formData.institutionId)
        };
        await axios.post('http://localhost:8080/api/students/register', payload);
        alert('Aluno cadastrado com sucesso!');
      } else if (role === 'teacher') {
        const payload = {
          name: formData.name,
          email: formData.email,
          cpf: formData.cpf,
          department: formData.department,
          password: formData.password,
          institutionId: Number(formData.institutionId)
        };
        await axios.post('http://localhost:8080/api/teachers/register', payload);
        alert('Professor cadastrado com sucesso!');
      } else if (role === 'company') {
        const payload = {
          name: formData.name,
          email: formData.email,
          cnpj: formData.cpf, // Reaproveitando o campo 'cpf' como CNPJ
          password: formData.password
        };
        await axios.post('http://localhost:8080/api/companies/register', payload);
        alert('Empresa cadastrada com sucesso!');
      }

      navigate('/');
    } catch (error) {
      console.error('Erro no cadastro:', error);
      alert('Erro ao cadastrar. Verifique os dados e tente novamente.');
    }
  };

  return (
    <div className="form-container">
      <h2>Cadastro</h2>

      <select value={role} onChange={(e) => setRole(e.target.value)}>
        <option value="student">Aluno</option>
        <option value="teacher">Professor</option>
        <option value="company">Empresa Parceira</option>
      </select>

      <form onSubmit={handleSubmit}>
        <input name="name" placeholder="Nome" onChange={handleChange} required />
        <input name="email" type="email" placeholder="Email" onChange={handleChange} required />
        <input
          name="cpf"
          placeholder={role === 'company' ? 'CNPJ' : 'CPF'}
          onChange={handleChange}
          required
        />
        <input name="password" type="password" placeholder="Senha" onChange={handleChange} required />

        {/* Dropdown de instituição — oculto para empresas */}
        {role !== 'company' && (
          <select
            name="institutionId"
            value={formData.institutionId}
            onChange={handleChange}
            required
          >
            <option value="">Selecione a instituição</option>
            {institutions.map((inst) => (
              <option key={inst.id} value={inst.id}>{inst.name}</option>
            ))}
          </select>
        )}

        {/* Campos específicos */}
        {role === 'student' && (
          <>
            <input name="rg" placeholder="RG" onChange={handleChange} required />
            <input name="address" placeholder="Endereço" onChange={handleChange} required />
            <input name="course" placeholder="Curso" onChange={handleChange} required />
          </>
        )}

        {role === 'teacher' && (
          <input name="department" placeholder="Departamento" onChange={handleChange} required />
        )}

        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <button type="submit">Cadastrar</button>
          <button
            type="button"
            className="secondary-button"
            onClick={() => navigate('/')}
          >
            Voltar
          </button>
        </div>
      </form>
    </div>
  );
}

import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import ProfessorDashboard from './pages/ProfessorDashboard';
import CompanyDashboard from './pages/CompanyDashboard';
import AlunoDashboard from './pages/AlunoDashboard'; 

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/cadastro" element={<Register />} />
        <Route path="/dashboard-professor" element={<ProfessorDashboard />} />
        <Route path="/dashboard-empresa" element={<CompanyDashboard />} />
        <Route path="/dashboard-aluno" element={<AlunoDashboard />} /> 
      </Routes>
    </BrowserRouter>
  );
}

export default App;

import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Register from './pages/Register';
import Login from './pages/Login';
import CompanyDashboard from './pages/CompanyDashboard';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/cadastro" element={<Register />} />
        <Route path="/dashboard-aluno" element={<div>Dashboard do Aluno</div>} />
        <Route path="/dashboard-professor" element={<div>Dashboard do Professor</div>} />
        <Route path="/dashboard-empresa" element={<CompanyDashboard />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

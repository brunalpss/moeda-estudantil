document.getElementById('cadastroForm').onsubmit = async function(e) {
  e.preventDefault();
  
  const professor = {
    name: document.getElementById('nome').value,
    email: document.getElementById('email').value,
    cpf: document.getElementById('cpf').value,
    department: document.getElementById('departamento').value,
    password: document.getElementById('senha').value,
    institutionId: document.getElementById('instituicao').value
  };

  try {
    const resp = await fetch(`${API_URL}/api/teachers/register`, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(professor)
    });

    if (resp.ok) {
      document.getElementById('msg').innerText = "Cadastro realizado com sucesso!";
      document.getElementById('msg').className = 'success';
      setTimeout(() => {
        window.location.href = 'login.html';
      }, 2000);
    } else {
      const error = await resp.json();
      document.getElementById('msg').innerText = error.message || "Erro ao cadastrar professor.";
      document.getElementById('msg').className = 'error';
    }
  } catch (error) {
    document.getElementById('msg').innerText = "Erro ao conectar com o servidor.";
    document.getElementById('msg').className = 'error';
  }
}; 
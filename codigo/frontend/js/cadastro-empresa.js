document.getElementById('cadastroEmpresaForm').onsubmit = async function(e) {
  e.preventDefault();
  const empresa = {
    nome: document.getElementById('nome').value,
    email: document.getElementById('email').value,
    senha: document.getElementById('senha').value
  };

  const resp = await fetch(`${API_URL}/empresa/cadastrar`, {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(empresa)
  });

  if (resp.ok) {
    document.getElementById('msg').innerText = "Cadastro realizado! Faça login.";
  } else {
    document.getElementById('msg').innerText = "Erro ao cadastrar.";
  }
}; 
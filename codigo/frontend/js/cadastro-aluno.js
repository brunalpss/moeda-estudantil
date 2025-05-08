document.getElementById('cadastroForm').onsubmit = async function(e) {
  e.preventDefault();
  const aluno = {
    nome: document.getElementById('nome').value,
    email: document.getElementById('email').value,
    cpf: document.getElementById('cpf').value,
    rg: document.getElementById('rg').value,
    instituicao: document.getElementById('instituicao').value,
    curso: document.getElementById('curso').value,
    senha: document.getElementById('senha').value
  };

  const resp = await fetch(`${API_URL}/aluno/cadastrar`, {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(aluno)
  });

  if (resp.ok) {
    document.getElementById('msg').innerText = "Cadastro realizado! Faça login.";
  } else {
    document.getElementById('msg').innerText = "Erro ao cadastrar.";
  }
}; 
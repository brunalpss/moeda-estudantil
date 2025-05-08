if (!getToken()) window.location.href = "login.html";

document.getElementById('cadastroVantagemForm').onsubmit = async function(e) {
  e.preventDefault();
  const vantagem = {
    nome: document.getElementById('nomeVantagem').value,
    descricao: document.getElementById('descricao').value,
    foto: document.getElementById('foto').value,
    custo: document.getElementById('custo').value
  };

  const resp = await fetch(`${API_URL}/empresa/vantagem`, {
    method: 'POST',
    headers: authHeaders(),
    body: JSON.stringify(vantagem)
  });

  if (resp.ok) {
    document.getElementById('msg').innerText = "Vantagem cadastrada!";
  } else {
    document.getElementById('msg').innerText = "Erro ao cadastrar vantagem.";
  }
}; 
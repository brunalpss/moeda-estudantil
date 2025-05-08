if (!getToken()) window.location.href = "login.html";

// Listar vantagens
fetch(`${API_URL}/vantagens`, { headers: authHeaders() })
  .then(resp => resp.json())
  .then(data => {
    let html = "<ul>";
    data.forEach(v => {
      html += `<li>
        <b>${v.nome}</b> - ${v.descricao} - <img src="${v.foto}" width="50"> - ${v.custo} moedas
        <button onclick="trocar(${v.id}, ${v.custo})">Trocar</button>
      </li>`;
    });
    html += "</ul>";
    document.getElementById('vantagens').innerHTML = html;
  });

function trocar(id, custo) {
  fetch(`${API_URL}/aluno/trocar-vantagem`, {
    method: 'POST',
    headers: authHeaders(),
    body: JSON.stringify({ idVantagem: id })
  })
  .then(resp => {
    if (resp.ok) {
      document.getElementById('msg').innerText = "Troca realizada! Você receberá um email.";
    } else {
      document.getElementById('msg').innerText = "Erro na troca.";
    }
  });
} 
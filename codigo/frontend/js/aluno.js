if (!getToken()) window.location.href = "login.html";

// Saldo
fetch(`${API_URL}/aluno/saldo`, { headers: authHeaders() })
  .then(resp => resp.json())
  .then(data => {
    document.getElementById('saldo').innerText = "Saldo: " + data.saldo + " moedas";
  });

// Extrato
fetch(`${API_URL}/aluno/extrato`, { headers: authHeaders() })
  .then(resp => resp.json())
  .then(data => {
    let html = "<h3>Extrato:</h3><ul>";
    data.transacoes.forEach(t => {
      html += `<li>${t.data} - ${t.descricao} - ${t.valor} moedas</li>`;
    });
    html += "</ul>";
    document.getElementById('extrato').innerHTML = html;
  }); 
if (!getToken()) window.location.href = "login.html";

// Saldo
fetch(`${API_URL}/professor/saldo`, { headers: authHeaders() })
  .then(resp => resp.json())
  .then(data => {
    document.getElementById('saldo').innerText = "Saldo: " + data.saldo + " moedas";
  });

// Envio de moedas
document.getElementById('envioMoedaForm').onsubmit = async function(e) {
  e.preventDefault();
  const envio = {
    emailAluno: document.getElementById('emailAluno').value,
    quantidade: document.getElementById('quantidade').value,
    motivo: document.getElementById('motivo').value
  };

  const resp = await fetch(`${API_URL}/professor/enviar-moeda`, {
    method: 'POST',
    headers: authHeaders(),
    body: JSON.stringify(envio)
  });

  if (resp.ok) {
    document.getElementById('msg').innerText = "Moedas enviadas!";
  } else {
    document.getElementById('msg').innerText = "Erro ao enviar moedas.";
  }
}; 
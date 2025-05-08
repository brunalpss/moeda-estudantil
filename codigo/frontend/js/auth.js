document.getElementById('loginForm').onsubmit = async function(e) {
  e.preventDefault();
  const email = document.getElementById('email').value;
  const senha = document.getElementById('senha').value;
  const tipo = document.getElementById('tipo').value;

  const resp = await fetch(`${API_URL}/login`, {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({ email, senha, tipo })
  });

  if (resp.ok) {
    const data = await resp.json();
    localStorage.setItem('token', data.token);
    if (tipo === "aluno") window.location.href = "dashboard-aluno.html";
    else if (tipo === "professor") window.location.href = "dashboard-professor.html";
    else window.location.href = "dashboard-empresa.html";
  } else {
    document.getElementById('msg').innerText = "Login inválido!";
  }
}; 
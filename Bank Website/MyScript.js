document.addEventListener("DOMContentLoaded", home);

function setContent(content) {
    document.getElementById("mainSection").innerHTML = content;
}

function home() {
    setContent(`
    <section class="p-4 bg-white">
      <h2 class="display-6 fw-bold mb-3">Welcome to Our Bank Website</h2>
      <p class="text-secondary">Explore the different pages aside to learn more about what we offer.</p>
    </section>
    `);
}

function about() {
    setContent(`
    <section class="p-4 bg-white">
      <h2 class="display-6 fw-bold mb-3">About Us</h2>
      <p class="text-secondary">We provide fast and reliable banking services.</p>
    </section>
    `);
}

function contact() {
    setContent(`
    <section class="p-4 bg-white">
      <h2 class="display-6 fw-bold mb-3">Contact Us</h2>
      <p>Email: ourbank@ourbank.com</p>
      <p>Phone: +91 9876543212</p>
    </section>
    `);
}

// Net Banking
function netbanking() {
    setContent(`
    <section class="bg-white p-4 rounded shadow col-md-6 mx-auto">
      <h2 class="h4 text-center mb-4">Account Access</h2>
      <button id="loginBtn" class="btn btn-primary me-2">Login</button>
      <button id="signupBtn" class="btn btn-success">Signup</button>

      <form id="loginForm" class="mt-3">
        <input type="email" class="form-control mb-2" placeholder="Email" required>
        <input type="password" class="form-control mb-2" placeholder="Password" required>
        <button class="btn btn-primary w-100">Login</button>
      </form>

      <form id="signupForm" class="d-none mt-3">
        <input type="text" class="form-control mb-2" placeholder="Full Name" required>
        <input type="email" class="form-control mb-2" placeholder="Email" required>
        <input type="password" class="form-control mb-2" placeholder="Password" required>
        <button class="btn btn-success w-100">Signup</button>
      </form>
    </section>
    `);

    document.getElementById("loginBtn").onclick = () => {
        loginForm.classList.remove("d-none");
        signupForm.classList.add("d-none");
    };

    document.getElementById("signupBtn").onclick = () => {
        signupForm.classList.remove("d-none");
        loginForm.classList.add("d-none");
    };
}

// Services
function services() {
    setContent(`
    <section class="h-100 d-flex flex-column">
      <h4 class="mb-3 text-center">Services</h4>
      <div class="d-flex justify-content-center gap-2 mb-3 flex-wrap">
        <button id="loanBtn" class="btn btn-outline-primary btn-sm">Loan EMI</button>
        <button id="depositBtn" class="btn btn-outline-success btn-sm">Deposit</button>
        <button id="accountBtn" class="btn btn-outline-secondary btn-sm">Open Account</button>
      </div>
      <div id="serviceFormArea" class="flex-grow-1 overflow-auto"></div>
    </section>
    `);
    serviceEvents();
}

function serviceEvents() {

    const formArea = document.getElementById("serviceFormArea");

    document.getElementById("loanBtn").onclick = () => {
        formArea.innerHTML = loanFormTemplate();
        loanEvents();
    };

    document.getElementById("depositBtn").onclick = () => {
        formArea.innerHTML = depositFormTemplate();
        depositEvents();
    };

    document.getElementById("accountBtn").onclick = () => {
        formArea.innerHTML = accountFormTemplate();
        accountEvents();
    };

    // Default view
    document.getElementById("loanBtn").click();
}

function loanFormTemplate() {
return `
<form id="loanForm" class="row g-4 p-2 m-2">

<div class="col-md-6">
<input type="text" id="holderName" class="form-control form-control-sm"
placeholder="Account Holder Name" required>
</div>

<div class="col-md-6">
<input type="text" id="accountNumber" class="form-control form-control-sm"
placeholder="Account Number" required>
</div>

<div class="col-md-4">
<select id="loanType" class="form-select form-select-sm" required>
<option value="" disabled selected>Select Loan Type</option>
<option value="PERSONAL">Personal</option>
<option value="HOME">Home</option>
<option value="CAR">Car</option>
</select>
</div>

<div class="col-md-4">
<input type="number" id="amount" class="form-control form-control-sm"
placeholder="Amount" required>
</div>

<div class="col-md-4">
<input type="number" id="duration" class="form-control form-control-sm"
placeholder="Years" required>
</div>

<div class="col-12">
<p id="rateDisplay" class="text-primary small d-none">
Interest Rate: <span id="rate"></span>%
</p>
</div>

<div class="col-12"><center>
<button class="btn btn-primary btn-sm w-25">Calculate EMI</button></center>
</div>

<div class="col-12">
<div id="emiResult" class="small mt-2"></div>
</div>

</form>
`;
}

function loanEvents() {

let interestRate = 0;

const loanType = document.getElementById("loanType");
const amount = document.getElementById("amount");
const duration = document.getElementById("duration");

loanType.addEventListener("change", function () {

    if (this.value === "HOME") {
        amount.min = 500000;
        duration.max = 30;
        interestRate = 9;
    }
    if (this.value === "PERSONAL") {
        amount.min = 10000;
        duration.max = 5;
        interestRate = 15;
    }
    if (this.value === "CAR") {
        amount.min = 100000;
        duration.max = 7;
        interestRate = 12;
    }

    document.getElementById("rate").innerText = interestRate;
    document.getElementById("rateDisplay").classList.remove("d-none");
});

document.getElementById("loanForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const p = parseFloat(amount.value);
    const years = parseFloat(duration.value);
    const r = interestRate / 12 / 100;
    const n = years * 12;
    const emi = (p * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
    document.getElementById("emiResult").innerHTML = `
        EMI: <b>₹ ${emi.toFixed(2)}</b> |
        Total: ₹ ${(emi*n).toFixed(2)}
    `;
});
}

function depositFormTemplate() {
return `
<form id="depositForm" class="row g-2 p-2 m-2">

<div class="col-md-6">
<input type="text" id="depAccount" class="form-control form-control-sm"
placeholder="Account Number" required>
</div>

<div class="col-md-6">
<input type="number" id="depAmount" class="form-control form-control-sm"
placeholder="Deposit Amount" required>
</div>

<div class="col-12">
<button class="btn btn-success btn-sm w-100">Deposit</button>
</div>

</form>
`;
}

function depositEvents() {
document.getElementById("depositForm").addEventListener("submit", function(e){
    e.preventDefault();
    alert("Deposit Successful!");
    this.reset();
});
}

function accountFormTemplate() {
return `
<form id="accountForm" class="row g-2 p-2 m-2">

<div class="col-md-4">
<input type="text" class="form-control form-control-sm"
placeholder="Full Name" required>
</div>

<div class="col-md-4">
<input type="email" class="form-control form-control-sm"
placeholder="Email" required>
</div>

<div class="col-md-4">
<input type="text" class="form-control form-control-sm"
placeholder="Address" required>
</div>

<div class="col-12">
<button class="btn btn-secondary btn-sm w-100">Open Account</button>
</div>

</form>
`;
}

function accountEvents() {
document.getElementById("accountForm").addEventListener("submit", function(e){
    e.preventDefault();
    alert("Account Created Successfully!");
    this.reset();
});
}
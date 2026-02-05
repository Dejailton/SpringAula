const baseUrl = window.location.origin;
const produtosTableBody = document.querySelector('#produtosTable tbody');
const produtoModalEl = document.getElementById('produtoModal');
let produtoModal = null;
if (produtoModalEl && window.bootstrap && bootstrap.Modal) {
  produtoModal = new bootstrap.Modal(produtoModalEl);
}
const produtoForm = document.getElementById('produtoForm');
const modalTitle = document.getElementById('modalTitle');
const alertPlaceholder = document.getElementById('alertPlaceholder');
const toastContainer = document.getElementById('toastContainer');
const loadingOverlay = document.getElementById('loadingOverlay');
const btnRefresh = document.getElementById('btnRefresh');
const searchInput = document.getElementById('searchInput');
const confirmDeleteModalEl = document.getElementById('confirmDeleteModal');
let confirmDeleteModal = null;
if (confirmDeleteModalEl && window.bootstrap && bootstrap.Modal) {
  confirmDeleteModal = new bootstrap.Modal(confirmDeleteModalEl);
}
const confirmDeleteYes = document.getElementById('confirmDeleteYes');
const totalCountEl = document.getElementById('totalCount');
const pageInfoEl = document.getElementById('pageInfo');
const paginationEl = document.getElementById('pagination');
const pageSizeSelect = document.getElementById('pageSizeSelect');

let allProducts = [];
let productPendingDelete = null;
let currentPage = 1;
let pageSize = Number(pageSizeSelect ? pageSizeSelect.value : 10);
let sortField = 'id';
let sortDir = 'asc';

const currencyFormatter = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' });
function formatCurrency(value) {
  if (value == null || value === '') return '';
  const n = Number(value);
  if (Number.isNaN(n)) return '';
  return currencyFormatter.format(n);
}
function parseCurrency(str) {
  if (str == null) return null;
  if (typeof str === 'number') return str;
  let s = String(str).replace(/\s/g, '').replace(/R\$/g, '').replace(/\u00A0/g, '');
  s = s.replace(/\./g, '').replace(/,/g, '.');
  s = s.replace(/[^0-9.\-]/g, '');
  if (s === '' || s === '-' ) return null;
  const n = Number(s);
  if (Number.isNaN(n)) return null;
  return n;
}

const precoInput = document.getElementById('preco');
if (precoInput) {
  precoInput.addEventListener('input', (e) => {
    const el = e.target;
    const digits = (el.value || '').replace(/\D/g, '');
    if (!digits) {
      el.value = '';
      return;
    }
    const cents = parseInt(digits, 10);
    const value = cents / 100;
    el.value = formatCurrency(value);
    try { el.selectionStart = el.selectionEnd = el.value.length; } catch (err) { }
    el.classList.remove('is-invalid');
  });
}

if (document.getElementById('btnNew')) document.getElementById('btnNew').addEventListener('click', () => openCreateModal());
if (btnRefresh) btnRefresh.addEventListener('click', () => fetchProducts());
if (produtoForm) produtoForm.addEventListener('submit', submitForm);
if (searchInput) searchInput.addEventListener('input', () => { currentPage = 1; renderProducts(getPaged(filterProducts(searchInput.value))); });
if (confirmDeleteYes) confirmDeleteYes.addEventListener('click', async () => {
  if (productPendingDelete != null) {
    confirmDeleteModal.hide();
    await doDeleteProduct(productPendingDelete);
    productPendingDelete = null;
  }
});
if (pageSizeSelect) pageSizeSelect.addEventListener('change', () => { pageSize = Number(pageSizeSelect.value); currentPage = 1; renderProducts(getPaged(filterProducts(searchInput.value))); });

(function initTooltips() {
  try {
    const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.forEach(el => new bootstrap.Tooltip(el));
  } catch (e) {
  }
})();

document.querySelectorAll('th.sortable').forEach(th => {
  th.style.cursor = 'pointer';
  th.addEventListener('click', () => {
    const field = th.getAttribute('data-field');
    if (field !== 'preco') return;

    if (sortField !== 'preco') {
      sortField = 'preco';
      sortDir = 'asc';
    } else if (sortDir === 'asc') {
      sortDir = 'desc';
    } else {
      sortField = 'id';
      sortDir = 'asc';
    }

    currentPage = 1;
    renderProducts(getPaged(filterProducts(searchInput.value)));
    updateSortIcons();
  });
});

function updateSortIcons() {
  document.querySelectorAll('th.sortable').forEach(th => {
    const icon = th.querySelector('i');
    const field = th.getAttribute('data-field');
    if (!icon) return;
    if (field === 'preco') {
      if (sortField === 'preco') {
        icon.className = (sortDir === 'asc' ? 'bi bi-arrow-up' : 'bi bi-arrow-down');
      } else {
        icon.className = 'bi bi-arrow-down-up';
      }
    } else {
      icon.className = 'bi bi-arrow-down-up';
    }
  });
}

function showLoading() { if (loadingOverlay) loadingOverlay.classList.remove('d-none'); }
function hideLoading() { if (loadingOverlay) loadingOverlay.classList.add('d-none'); }

function showToast(message, type = 'primary', timeout = 4000) {
  if (!toastContainer) {
    try { alert(message); } catch (e) { console.log(type.toUpperCase() + ': ' + message); }
    return;
  }
  const toastEl = document.createElement('div');
  toastEl.className = 'toast align-items-center text-bg-' + (type === 'danger' ? 'danger' : (type === 'warning' ? 'warning' : (type === 'success' ? 'success' : 'primary'))) + ' border-0';
  toastEl.setAttribute('role', 'alert');
  toastEl.setAttribute('aria-live', 'assertive');
  toastEl.setAttribute('aria-atomic', 'true');
  toastEl.innerHTML = `
    <div class="d-flex">
      <div class="toast-body">${escapeHtml(message)}</div>
      <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Fechar"></button>
    </div>
  `;
  toastContainer.appendChild(toastEl);
  const toast = new bootstrap.Toast(toastEl, { delay: timeout });
  toast.show();
  toastEl.addEventListener('hidden.bs.toast', () => toastEl.remove());
}

function showAlert(message, type = 'info') { showToast(message, type === 'danger' ? 'danger' : type === 'warning' ? 'warning' : type === 'success' ? 'success' : 'primary'); }

async function fetchProducts() {
  showLoading();
  try {
    const res = await fetch(`${baseUrl}/produtos`);
    if (!res.ok) {
      const text = await res.text();
      throw new Error(text || 'Falha ao buscar produtos');
    }
    const produtos = await res.json();
    allProducts = produtos || [];
    totalCountEl.textContent = `${allProducts.length} produtos`;
    renderProducts(getPaged(allProducts));
    updateSortIcons();
  } catch (err) {
    showAlert(err.message, 'danger');
  } finally {
    hideLoading();
  }
}

function filterProducts(query) { const q = (query || '').trim().toLowerCase(); if (!q) return allProducts; return allProducts.filter(p => (p.nome || '').toLowerCase().includes(q)); }

function getPaged(list) {
  const sorted = [...(list || [])].sort((a,b) => {
    const av = a[sortField];
    const bv = b[sortField];
    if (av == null && bv == null) return 0;
    if (av == null) return sortDir === 'asc' ? -1 : 1;
    if (bv == null) return sortDir === 'asc' ? 1 : -1;
    if (typeof av === 'string') return sortDir === 'asc' ? av.localeCompare(bv) : bv.localeCompare(av);
    return sortDir === 'asc' ? av - bv : bv - av;
  });
  const start = (currentPage -1) * pageSize;
  const page = sorted.slice(start, start + pageSize);
  const totalPages = Math.max(1, Math.ceil((sorted.length || 0)/pageSize));
  pageInfoEl.textContent = `Página ${currentPage} de ${totalPages}`;
  renderPagination(totalPages);
  return page;
}

function renderPagination(totalPages) {
  paginationEl.innerHTML = '';
  for (let p=1; p<=totalPages; p++) {
    const li = document.createElement('li');
    li.className = 'page-item ' + (p===currentPage ? 'active' : '');
    li.innerHTML = `<a class="page-link" href="#">${p}</a>`;
    li.addEventListener('click', (e) => { e.preventDefault(); currentPage = p; renderProducts(getPaged(filterProducts(searchInput.value))); });
    paginationEl.appendChild(li);
  }
}

function renderProducts(produtos) {
  produtosTableBody.innerHTML = '';
  if (!produtos || produtos.length === 0) {
    produtosTableBody.innerHTML = '<tr><td colspan="4" class="text-center text-muted">Nenhum produto encontrado</td></tr>';
    return;
  }
  produtos.forEach(p => {
    const tr = document.createElement('tr');
    const precoStr = (p.preco != null) ? formatCurrency(p.preco) : '';
    tr.innerHTML = `
      <td>${p.id ?? ''}</td>
      <td>${escapeHtml(p.nome)}</td>
      <td class="text-end">${precoStr}</td>
      <td>
        <button class="btn btn-sm btn-outline-primary me-2" data-id="${p.id}" onclick="openEditModal(${p.id})">Editar</button>
        <button class="btn btn-sm btn-outline-danger" data-id="${p.id}" onclick="openConfirmDelete(${p.id})">Remover</button>
      </td>
    `;
    produtosTableBody.appendChild(tr);
  });
}

function openCreateModal() {
  modalTitle.textContent = 'Novo Produto';
  produtoForm.classList.remove('was-validated');
  produtoForm.reset();
  document.getElementById('produtoId').value = '';
  clearFieldErrors();
  if (produtoModal) produtoModal.show();
  setTimeout(() => document.getElementById('nome').focus(), 250);
}

async function openEditModal(id) {
  showLoading();
  try {
    const res = await fetch(`${baseUrl}/produtos/${id}`);
    if (!res.ok) {
      const text = await res.text();
      throw new Error(text || 'Produto não encontrado');
    }
    const p = await res.json();
    modalTitle.textContent = 'Editar Produto';
    produtoForm.classList.remove('was-validated');
    clearFieldErrors();
    document.getElementById('produtoId').value = p.id ?? '';
    document.getElementById('nome').value = p.nome ?? '';
    document.getElementById('preco').value = (p.preco != null) ? formatCurrency(p.preco) : '';
    if (produtoModal) produtoModal.show();
    setTimeout(() => document.getElementById('nome').focus(), 250);
  } catch (err) {
    showAlert(err.message, 'danger');
  } finally {
    hideLoading();
  }
}

async function submitForm(event) {
  event.preventDefault();
  clearFieldErrors();
  if (!produtoForm.checkValidity()) {
    produtoForm.classList.add('was-validated');
    return;
  }

  const id = document.getElementById('produtoId').value;
  const nome = document.getElementById('nome').value.trim();
  const preco = parseCurrency(document.getElementById('preco').value);

  if (!nome || preco == null || Number.isNaN(preco) || preco < 0) {
    showAlert('Preencha nome e preço válidos', 'warning');
    return;
  }

  const method = id ? 'PUT' : 'POST';
  const url = id ? `${baseUrl}/produtos/${id}` : `${baseUrl}/produtos`;

  const saveBtn = document.getElementById('saveBtn');
  saveBtn.disabled = true;
  showLoading();
  try {
    const res = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nome, preco: preco })
    });
    if (!res.ok) {
      const data = await parseErrorResponse(res);
      showApiErrors(data);
      throw new Error(data.message || 'Erro na requisição');
    }
    await res.json();
    if (produtoModal) produtoModal.hide();
    fetchProducts();
    showToast(id ? 'Produto atualizado.' : 'Produto criado.', 'success');
  } catch (err) {
    showAlert(err.message, 'danger');
  } finally {
    hideLoading();
    saveBtn.disabled = false;
  }
}

function openConfirmDelete(id) {
  productPendingDelete = id;
  if (confirmDeleteModal) confirmDeleteModal.show();
}

async function doDeleteProduct(id) {
  showLoading();
  try {
    const res = await fetch(`${baseUrl}/produtos/${id}`, { method: 'DELETE' });
    if (!res.ok) {
      const text = await res.text();
      throw new Error(text || 'Falha ao remover');
    }
    showToast('Produto removido.', 'success');
    fetchProducts();
  } catch (err) {
    showAlert(err.message, 'danger');
  } finally {
    hideLoading();
    if (confirmDeleteModal) confirmDeleteModal.hide();
  }
}

async function parseErrorResponse(res) {
  try {
    const json = await res.json();
    return json;
  } catch (e) {
    try {
      const text = await res.text();
      return { message: text || res.statusText };
    } catch (e2) {
      return { message: res.statusText };
    }
  }
}

function clearFieldErrors() {
  ['nome', 'preco'].forEach(id => {
    const el = document.getElementById(id);
    if (!el) return;
    el.classList.remove('is-invalid');
    const fb = el.closest('.mb-3')?.querySelector('.invalid-feedback');
    if (fb) {
      if (id === 'nome') fb.textContent = 'O nome é obrigatório.';
      if (id === 'preco') fb.textContent = 'Informe um preço válido (0 ou maior).';
    }
    if (el.setCustomValidity) el.setCustomValidity('');
  });
}

function showApiErrors(data) {
  if (data && typeof data === 'object' && data.errors && typeof data.errors === 'object') {
    const errs = data.errors;
    let shown = false;
    Object.keys(errs).forEach(k => {
      const msg = errs[k];
      const el = document.getElementById(k);
      if (el) {
        el.classList.add('is-invalid');
        const fb = el.closest('.mb-3')?.querySelector('.invalid-feedback');
        if (fb) fb.textContent = msg;
        if (el.setCustomValidity) el.setCustomValidity(msg);
        shown = true;
      } else {
        showToast(msg, 'danger');
      }
    });
    if (shown) return;
  }

  if (data && typeof data === 'object') {
    if (data.message) {
      showToast(data.message, 'danger');
      return;
    }
    if (data.error && typeof data.error === 'string') {
      showToast(data.error, 'danger');
      return;
    }
  }

  showToast('Erro desconhecido', 'danger');
}


fetchProducts();

window.openEditModal = openEditModal;
window.openConfirmDelete = openConfirmDelete;

function escapeHtml(s) {
  if (s == null) return '';
  return String(s)
    .replaceAll('&', '&amp;')
    .replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;')
    .replaceAll('"', '&quot;')
    .replaceAll("'", '&#039;');
}

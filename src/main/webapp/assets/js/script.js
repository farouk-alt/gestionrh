// Animations pour les notifications
document.addEventListener("DOMContentLoaded", () => {
  // Animation des alertes
  const alerts = document.querySelectorAll(".alert")
  alerts.forEach((alert) => {
    // Ajouter une classe pour l'animation d'entrée
    alert.classList.add("show")

    // Fermeture automatique après 5 secondes
    setTimeout(() => {
      if (alert) {
        // Ensure bootstrap is available before using it
        if (typeof bootstrap !== "undefined") {
          const bsAlert = new bootstrap.Alert(alert)
          bsAlert.close()
        } else {
          console.error("Bootstrap is not defined. Ensure it is properly loaded.")
          // Optionally, remove the alert element directly if bootstrap is not available
          alert.remove()
        }
      }
    }, 5000)
  })

  // Animation des badges
  const badges = document.querySelectorAll(".badge")
  badges.forEach((badge) => {
    badge.addEventListener("mouseenter", function () {
      this.style.transform = "scale(1.1)"
      this.style.transition = "transform 0.3s ease"
    })

    badge.addEventListener("mouseleave", function () {
      this.style.transform = "scale(1)"
    })
  })

  // Animation des boutons
  const buttons = document.querySelectorAll(".btn")
  buttons.forEach((button) => {
    button.addEventListener("mouseenter", function () {
      this.classList.add("btn-pulse")
    })

    button.addEventListener("mouseleave", function () {
      this.classList.remove("btn-pulse")
    })
  })

  // Animation des cartes
  const cards = document.querySelectorAll(".card")
  cards.forEach((card) => {
    card.addEventListener("mouseenter", function () {
      this.style.transform = "translateY(-5px)"
      this.style.boxShadow = "0 10px 20px rgba(0, 0, 0, 0.1)"
      this.style.transition = "all 0.3s ease"
    })

    card.addEventListener("mouseleave", function () {
      this.style.transform = "translateY(0)"
      this.style.boxShadow = "0 2px 4px rgba(0, 0, 0, 0.1)"
    })
  })

  // Validation des formulaires
  const forms = document.querySelectorAll("form")
  forms.forEach((form) => {
    form.addEventListener("submit", (event) => {
      if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()

        // Ajouter une animation de secousse au formulaire
        form.classList.add("form-shake")
        setTimeout(() => {
          form.classList.remove("form-shake")
        }, 500)
      }

      form.classList.add("was-validated")
    })
  })

  // Confirmation pour les actions de suppression
  const deleteButtons = document.querySelectorAll('.btn-delete, [data-action="delete"]')
  deleteButtons.forEach((button) => {
    button.addEventListener("click", (event) => {
      if (!confirm("Êtes-vous sûr de vouloir supprimer cet élément ? Cette action est irréversible.")) {
        event.preventDefault()
      }
    })
  })

  // Datepicker pour les champs de date
  const datepickers = document.querySelectorAll('input[type="date"]')
  datepickers.forEach((datepicker) => {
    // Ajouter une classe pour le style
    datepicker.classList.add("datepicker-styled")

    // Vérifier que la date de fin est après la date de début pour les demandes de congé
    if (datepicker.id === "dateFin") {
      datepicker.addEventListener("change", function () {
        const dateDebut = document.getElementById("dateDebut")
        if (dateDebut && this.value < dateDebut.value) {
          alert("La date de fin doit être postérieure à la date de début.")
          this.value = ""
        }
      })
    }
  })

  // Calcul automatique du nombre de jours de congé
  const dateDebut = document.getElementById("dateDebut")
  const dateFin = document.getElementById("dateFin")
  const nombreJours = document.getElementById("nombreJours")

  if (dateDebut && dateFin && nombreJours) {
    const calculerNombreJours = () => {
      if (dateDebut.value && dateFin.value) {
        const debut = new Date(dateDebut.value)
        const fin = new Date(dateFin.value)

        if (fin >= debut) {
          const diffTime = Math.abs(fin - debut)
          const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1 // +1 car on compte le jour de début
          nombreJours.value = diffDays

          // Vérifier si l'employé a assez de jours de congé
          const soldeConge = document.getElementById("soldeConge")
          if (soldeConge && Number.parseInt(soldeConge.value) < diffDays) {
            alert("Attention : Le nombre de jours demandés dépasse votre solde de congés disponible.")
          }
        }
      }
    }

    dateDebut.addEventListener("change", calculerNombreJours)
    dateFin.addEventListener("change", calculerNombreJours)
  }

  // Filtrage des tableaux
  const tableFilter = document.getElementById("tableFilter")
  if (tableFilter) {
    tableFilter.addEventListener("keyup", function () {
      const filterValue = this.value.toLowerCase()
      const table = document.querySelector(".table-filterable")

      if (table) {
        const rows = table.querySelectorAll("tbody tr")

        rows.forEach((row) => {
          let found = false
          const cells = row.querySelectorAll("td")

          cells.forEach((cell) => {
            if (cell.textContent.toLowerCase().indexOf(filterValue) > -1) {
              found = true
            }
          })

          if (found) {
            row.style.display = ""
          } else {
            row.style.display = "none"
          }
        })
      }
    })
  }

  // Tri des tableaux
  const tableHeaders = document.querySelectorAll(".table-sortable th[data-sort]")
  tableHeaders.forEach((header) => {
    header.addEventListener("click", function () {
      const table = this.closest("table")
      const sortBy = this.dataset.sort
      const sortDirection = this.classList.contains("sort-asc") ? "desc" : "asc"

      // Réinitialiser les classes de tri sur tous les en-têtes
      tableHeaders.forEach((h) => {
        h.classList.remove("sort-asc", "sort-desc")
      })

      // Ajouter la classe de tri sur l'en-tête actuel
      this.classList.add(`sort-${sortDirection}`)

      // Trier les lignes
      const rows = Array.from(table.querySelectorAll("tbody tr"))
      rows.sort((a, b) => {
        const aValue =
          a.querySelector(`td[data-${sortBy}]`).dataset[sortBy] ||
          a.querySelector(`td:nth-child(${Array.from(a.parentNode.children).indexOf(a) + 1})`).textContent
        const bValue =
          b.querySelector(`td[data-${sortBy}]`).dataset[sortBy] ||
          b.querySelector(`td:nth-child(${Array.from(b.parentNode.children).indexOf(b) + 1})`).textContent

        if (sortDirection === "asc") {
          return aValue.localeCompare(bValue, undefined, { numeric: true })
        } else {
          return bValue.localeCompare(aValue, undefined, { numeric: true })
        }
      })

      // Réinsérer les lignes triées
      const tbody = table.querySelector("tbody")
      rows.forEach((row) => tbody.appendChild(row))
    })
  })

  // Afficher/masquer le mot de passe
  const togglePasswordButtons = document.querySelectorAll(".toggle-password")
  togglePasswordButtons.forEach((button) => {
    button.addEventListener("click", function () {
      const passwordField = document.getElementById(this.dataset.target)

      if (passwordField) {
        if (passwordField.type === "password") {
          passwordField.type = "text"
          this.innerHTML = '<i class="bi bi-eye-slash"></i>'
        } else {
          passwordField.type = "password"
          this.innerHTML = '<i class="bi bi-eye"></i>'
        }
      }
    })
  })

  // Initialiser les tooltips Bootstrap
  const tooltips = document.querySelectorAll('[data-bs-toggle="tooltip"]')
  tooltips.forEach((tooltip) => {
    new bootstrap.Tooltip(tooltip)
  })

  // Initialiser les popovers Bootstrap
  const popovers = document.querySelectorAll('[data-bs-toggle="popover"]')
  popovers.forEach((popover) => {
    new bootstrap.Popover(popover)
  })
})

// Ajouter les styles CSS pour les animations
document.addEventListener("DOMContentLoaded", () => {
  const style = document.createElement("style")
  style.textContent = `
        @keyframes btn-pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.05); }
            100% { transform: scale(1); }
        }

        .btn-pulse {
            animation: btn-pulse 0.5s ease-in-out;
        }

        @keyframes form-shake {
            0%, 100% { transform: translateX(0); }
            10%, 30%, 50%, 70%, 90% { transform: translateX(-5px); }
            20%, 40%, 60%, 80% { transform: translateX(5px); }
        }

        .form-shake {
            animation: form-shake 0.5s cubic-bezier(.36,.07,.19,.97) both;
        }

        .datepicker-styled {
            cursor: pointer;
            background-color: #fff;
            background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar" viewBox="0 0 16 16"><path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/></svg>');
            background-repeat: no-repeat;
            background-position: right 0.5rem center;
            background-size: 16px 16px;
        }

        .table-sortable th[data-sort] {
            cursor: pointer;
            position: relative;
        }

        .table-sortable th[data-sort]::after {
            content: '⇅';
            position: absolute;
            right: 8px;
            color: #999;
        }

        .table-sortable th.sort-asc::after {
            content: '↑';
            color: #000;
        }

        .table-sortable th.sort-desc::after {
            content: '↓';
            color: #000;
        }
    `
  document.head.appendChild(style)
})
document.addEventListener('DOMContentLoaded', () => {
  const table = document.getElementById("employeTable");
  const headers = table.querySelectorAll("th[data-sort]");
  const tbody = table.querySelector("tbody");

  const getCellValue = (tr, idx) => tr.children[idx].textContent.trim().toLowerCase();

  const comparer = (idx, asc) => (a, b) => {
    const v1 = getCellValue(asc ? a : b, idx);
    const v2 = getCellValue(asc ? b : a, idx);
    return isNaN(v1) || isNaN(v2) ? v1.localeCompare(v2) : v1 - v2;
  };

  headers.forEach((th, idx) => {
    let asc = true;
    th.style.cursor = "pointer";
    th.addEventListener("click", () => {
      const rows = Array.from(tbody.querySelectorAll("tr"));
      rows.sort(comparer(idx, asc));
      rows.forEach(row => tbody.appendChild(row));
      asc = !asc;

      // Optionnel : ajouter ou modifier l'affichage flèches ▲▼
      headers.forEach(h => h.innerHTML = h.dataset.sort); // reset
      th.innerHTML = th.dataset.sort + (asc ? " ▲" : " ▼");
    });
  });
});



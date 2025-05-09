/**
 * Fichier JavaScript pour les animations et interactions de l'application
 */

// Fonction pour afficher une notification
function showNotification(message, type = "info", duration = 5000) {
  const container = document.getElementById("notification-container")

  if (!container) {
    console.error("Conteneur de notification non trouvé")
    return
  }

  // Créer l'élément de notification
  const notification = document.createElement("div")
  notification.className = `notification alert alert-${type} alert-dismissible fade show`
  notification.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `

  // Ajouter la notification au conteneur
  container.appendChild(notification)

  // Animer l'entrée
  setTimeout(() => {
    notification.classList.add("show")
  }, 10)

  // Supprimer après la durée spécifiée
  setTimeout(() => {
    notification.classList.remove("show")
    notification.classList.add("fade")
    setTimeout(() => {
      notification.remove()
    }, 300)
  }, duration)

  // Permettre la fermeture manuelle
  const closeButton = notification.querySelector(".btn-close")
  if (closeButton) {
    closeButton.addEventListener("click", () => {
      notification.classList.remove("show")
      notification.classList.add("fade")
      setTimeout(() => {
        notification.remove()
      }, 300)
    })
  }
}

// Animation des éléments au chargement de la page
document.addEventListener("DOMContentLoaded", () => {
  // Animer les tableaux
  const tables = document.querySelectorAll(".table-responsive")
  tables.forEach((table, index) => {
    setTimeout(() => {
      table.classList.add("animate-fade-in")
    }, 200 * index)
  })

  // Animer les formulaires
  const forms = document.querySelectorAll("form")
  forms.forEach((form) => {
    form.classList.add("animate-fade-in")
  })

  // Effet de survol sur les lignes de tableau
  const tableRows = document.querySelectorAll("tbody tr")
  tableRows.forEach((row) => {
    row.addEventListener("mouseenter", () => {
      row.style.transition = "background-color 0.3s ease"
      row.style.backgroundColor = "rgba(0, 123, 255, 0.05)"
    })

    row.addEventListener("mouseleave", () => {
      row.style.backgroundColor = ""
    })
  })

  // Animation des boutons
  const buttons = document.querySelectorAll(".btn")
  buttons.forEach((button) => {
    button.addEventListener("mouseenter", () => {
      button.classList.add("animate-pulse")
    })

    button.addEventListener("mouseleave", () => {
      button.classList.remove("animate-pulse")
    })
  })
})

// Animation de pulsation pour les boutons
document.addEventListener("DOMContentLoaded", () => {
  // Ajouter la classe CSS pour l'animation de pulsation
  const style = document.createElement("style")
  style.textContent = `
        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.05); }
            100% { transform: scale(1); }
        }
        
        .animate-pulse {
            animation: pulse 0.5s ease-in-out;
        }
    `
  document.head.appendChild(style)
})

// Validation des formulaires côté client
document.addEventListener("DOMContentLoaded", () => {
  const forms = document.querySelectorAll("form")

  forms.forEach((form) => {
    form.addEventListener("submit", (e) => {
      const requiredFields = form.querySelectorAll("[required]")
      let isValid = true

      requiredFields.forEach((field) => {
        if (!field.value.trim()) {
          isValid = false
          field.classList.add("is-invalid")

          // Créer un message d'erreur si inexistant
          let feedback = field.nextElementSibling
          if (!feedback || !feedback.classList.contains("invalid-feedback")) {
            feedback = document.createElement("div")
            feedback.className = "invalid-feedback"
            feedback.textContent = "Ce champ est obligatoire"
            field.parentNode.insertBefore(feedback, field.nextSibling)
          }
        } else {
          field.classList.remove("is-invalid")
        }
      })

      if (!isValid) {
        e.preventDefault()
        showNotification("Veuillez remplir tous les champs obligatoires", "danger")
      }
    })
  })
})

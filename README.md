# Résumé des Travaux Réalisés

| Section                                      |
|----------------------------------------------|
| <span style="color:#007acc">[Mise à Jour du Head](#mise-à-jour-du-head)</span>   |
| <span style="color:#007acc">[Améliorations dans le Fonctionnement de Datatable](#améliorations-dans-le-fonctionnement-de-datatable)</span>   |
| <span style="color:#007acc">[Ajout d'une Couche de Réactivité pour le Dossier des LOGS](#ajout-dune-couche-de-réactivité-pour-le-dossier-des-logs)</span>   |
| <span style="color:#007acc">[Modifications des Modals et Tableau](#modifications-des-modals-et-tableau)</span>   |
| <span style="color:#007acc">[Utilisation des Server Sent Events](#utilisation-des-server-sent-events)</span>   |

---

## <span style="color:#007acc">Mise à Jour du Head</span>

Passage de Bootstrap à la version 5.3.1. <br> Ajout de la bibliothèque d'icônes Font Awesome.

## <span style="color:#007acc">Améliorations dans le Fonctionnement de Datatable</span>

Plutôt que d'afficher directement un tableau côté serveur via Thymeleaf, nous avons repensé le fonctionnement de "datatable". Désormais, les données sont chargées de manière asynchrone en utilisant un appel AJAX via un contrôleur Spring dédié. Cela a permis d'effectuer des mises à jour sans rechargement de la page.

## <span style="color:#007acc">Ajout d'une Couche de Réactivité pour le Dossier des LOGS</span>

Nous avons intégré une couche de réactivité au projet, lui permettant de détecter les événements d'ajout, de mise à jour et de suppression dans le dossier. Cette couche actualise automatiquement l'interface utilisateur de l'outil de suivi, offrant aux utilisateurs un accès en temps réel aux informations les plus récentes relatives à la migration.

## <span style="color:#007acc">Modifications des Modals et Tableau</span>

Des ajustements ont été apportés aux modals de l'outil ainsi qu'aux tableaux pour les rendre plus conviviaux et cohérents avec le reste de l'interface. Ces modifications ont simplifié l'interaction de l'utilisateur avec les modals et ont amélioré la fluidité globale de l'utilisation de l'outil.

## <span style="color:#007acc">Utilisation des Server Sent Events</span>

Nous avons utilisé EventSource pour écouter un contrôleur côté back qui émet des événements en temps réel. En cas de modification dans les dossiers de logs, cette approche permet au front-end de se rafraîchir automatiquement.

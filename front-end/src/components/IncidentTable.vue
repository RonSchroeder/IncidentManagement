<template>
  <div class="container mt-5">
    <!-- Header -->
    <div class="text-center mb-4">
      <h2 class="text">Incident Management</h2>
    </div>

    <!-- Action Buttons (Right-aligned) -->
    <div class="d-flex justify-content-end mb-3">
      <button
        class="btn btn-success btn-sm me-2"
        @click="openCreateModal"
      >
        Add Incident
      </button>
      <button
        class="btn btn-success btn-sm me-2"
        :disabled="selectedIds.length !== 1"
        @click="openEditModal"
      >
        Edit Incident
      </button>
      <button
        class="btn btn-success btn-sm me-2"
        :disabled="selectedIds.length === 0"
        @click="deleteSelectedIncidents"
      >
        Delete Incident
      </button>
    </div>

    <!-- Incident Table -->
    <table class="table table-striped table-hover">
      <thead class="table-dark">
        <tr>
          <th>
            <input
              type="checkbox"
              @change="toggleSelectAll($event)"
              :checked="allSelected"
            />
          </th>
          <th>ID</th>
          <th>Title</th>
          <th>Description</th>
          <th>Status</th>
          <th>Created By</th>
          <th>Created At</th>
          <th>Updated By</th>
          <th>Updated At</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="incident in incidents"
          :key="incident.id"
          :class="{ 'table-active': isSelected(incident.id) }"
        >
          <td>
            <input type="checkbox" :value="incident.id" v-model="selectedIds" />
          </td>
          <td>{{ incident.id }}</td>
          <td>{{ incident.title }}</td>
          <td>{{ incident.description }}</td>
          <td>
            <span
              class="badge"
              :class="{
                'bg-red': incident.status === 'OPEN',
                'bg-yellow': incident.status === 'IN_PROGRESS',
                'bg-green': incident.status === 'CLOSED',
              }"
            >
              {{ incident.status }}
            </span>
          </td>
          <td>{{ incident.createdBy || 'N/A' }}</td>
          <td>{{ formatDate(incident.createdAt) }}</td>
          <td>{{ incident.updatedBy || 'N/A' }}</td>
          <td>{{ formatDate(incident.updatedAt) }}</td>
        </tr>
      </tbody>
    </table>

    <!-- Modal for Adding or Editing -->
    <div
      class="modal fade"
      id="incidentModal"
      tabindex="-1"
      aria-labelledby="incidentModalLabel"
      aria-hidden="true"
      ref="modal"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header bg">
            <h5 class="modal-title text-white" id="incidentModalLabel">
              {{ isEditing ? "Edit Incident" : "Add Incident" }}
            </h5>
            <button type="button" class="btn-close btn-close-white" @click="closeModal"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="handleFormSubmit">
              <div class="mb-3">
                <label for="title" class="form-label">Title:</label>
                <input
                  type="text"
                  id="title"
                  class="form-control"
                  v-model="formData.title"
                  required
                  maxlength="100"
                />
              </div>
              <div class="mb-3">
                <label for="description" class="form-label">Description:</label>
                <textarea
                  id="description"
                  class="form-control"
                  v-model="formData.description"
                  maxlength="500"
                ></textarea>
              </div>
              <div class="mb-3">
                <label for="status" class="form-label">Status:</label>
                <select
                  id="status"
                  class="form-select"
                  v-model="formData.status"
                  required
                >
                  <option value="OPEN">OPEN</option>
                  <option value="IN_PROGRESS">IN_PROGRESS</option>
                  <option value="CLOSED">CLOSED</option>
                </select>
              </div>
              <div class="d-flex justify-content-end">
                <button type="submit" class="btn btn-success btn-sm me-2">
                  {{ isEditing ? "Edit" : "Add" }}
                </button>
                <button
                  type="button"
                  class="btn btn-secondary btn-sm"
                  @click="closeModal"
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Modal from "bootstrap/js/dist/modal";

export default {
  data() {
    return {
      incidents: [], // List of incidents
      selectedIds: [], // Selected incident IDs
      showModal: false, // Modal visibility
      isEditing: false, // Determines if editing or adding
      formData: {
        id: null,
        title: "",
        description: "",
        status: "OPEN",
      },
    };
  },
  computed: {
    allSelected() {
      return (
        this.incidents.length > 0 &&
        this.selectedIds.length === this.incidents.length
      );
    },
  },
  methods: {
    queryAllIncidents(page = 0, size = 20) {
    axios
      .post("http://localhost:8080/api/incidents/queryAllIncidents", {
        page: page,
        size: size
      })
      .then((response) => {
        const data = response.data.data;
        this.incidents = data.content; // 提取 content 数据
        this.pagination = {
          pageNumber: data.number,
          pageSize: data.size,
          totalPages: data.totalPages,
          totalElements: data.totalElements
        }; // 更新分页信息
      })
      .catch((error) => {
        console.error("Error fetching incidents:", error);
      });
    }
    ,
    toggleSelectAll(event) {
      if (event.target.checked) {
        this.selectedIds = this.incidents.map((incident) => incident.id);
      } else {
        this.selectedIds = [];
      }
    },
    isSelected(id) {
      return this.selectedIds.includes(id);
    },
    openCreateModal() {
      this.isEditing = false;
      this.formData = {
        id: null,
        title: "",
        description: "",
        status: "OPEN",
      };
      this.toggleModal();
    },
    openEditModal() {
      if (this.selectedIds.length === 1) {
        const selectedIncident = this.incidents.find(
          (incident) => incident.id === this.selectedIds[0]
        );
        if (selectedIncident) {
          this.isEditing = true;
          this.formData = { ...selectedIncident };
          this.toggleModal();
        }
      }
    },
    deleteSelectedIncidents() {
      const payload = this.selectedIds.map((id) => ({ id }));
      axios
        .post("http://localhost:8080/api/incidents/deleteIncident", payload)
        .then(() => {
          this.queryAllIncidents();
          this.selectedIds = [];
        })
        .catch((error) => {
          console.error("Error deleting incidents:", error);
        });
    },
    toggleModal() {
      const modalElement = this.$refs.modal;
      if (modalElement) {
        const modal = Modal.getOrCreateInstance(modalElement);
        modal.show();
      }
    },
    closeModal() {
      const modalElement = this.$refs.modal;
      if (modalElement) {
        const modal = Modal.getInstance(modalElement);
        modal.hide();
      }
    },
    handleFormSubmit() {
      const apiEndpoint = this.isEditing
        ? "http://localhost:8080/api/incidents/modifyIncident"
        : "http://localhost:8080/api/incidents/createIncident";

      axios
        .post(apiEndpoint, this.formData)
        .then(() => {
          this.queryAllIncidents();
          this.closeModal();
        })
        .catch((error) => {
          console.error("Error submitting form:", error);
        });
    },
    formatDate(date) {
      if (!date) return "N/A";
      return new Intl.DateTimeFormat("en-US", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit"
      }).format(new Date(date));
    }
  },
  mounted() {
    this.queryAllIncidents();
  },
};
</script>

<style>
.text {
  color: #000000;
}
.table {
  background-color: #f2f2f2;
}
.bg {
  background-color: #db0011 !important;
}
.btn-action {
  background-color: #db0011;
  color: #ffffff;
  border: none;
  transition: all 0.3s ease;
}
.btn-action:hover {
  background-color: #a3000d; /* Darker red on hover */
}
.btn-action:disabled {
  background-color: #cccccc;
  color: #ffffff;
  border: none;
}
.bg-red {
  background-color: #db0011 !important;
}
.bg-yellow {
  background-color: #ffc107 !important;
}
.bg-green {
  background-color: #28a745 !important;
}
.table-active {
  background-color: #f8f9fa !important;
}
</style>

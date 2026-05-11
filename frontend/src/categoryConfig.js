const CONFIG = {
  _default: {
    title: "Book service",
    icon: "📋",
    fields: [
      { name: "name", label: "Full name", type: "text", section: "Contact", required: true },
      { name: "phone", label: "Phone", type: "tel", section: "Contact", required: true },
      { name: "notes", label: "Notes", type: "textarea", section: "Booking", required: false, rows: 2 },
      { name: "date", label: "Preferred date", type: "date", section: "Booking", required: true },
    ],
  },
  Catering: {
    title: "Catering booking",
    icon: "🍽️",
    extra: "menu",
    fields: [
      { name: "name", label: "Full name", type: "text", section: "Contact", required: true },
      { name: "phone", label: "Phone", type: "tel", section: "Contact", required: true },
      { name: "people", label: "Number of people", type: "number", section: "Event", required: true, attrs: { min: "1", step: "1" } },
      { name: "event", label: "Event type", type: "text", section: "Event", required: true },
      { name: "date", label: "Date", type: "date", section: "Event", required: true },
    ],
  },
  "Baby Care": {
    title: "Baby care",
    icon: "👶",
    fields: [
      { name: "name", label: "Full name", type: "text", section: "Contact", required: true },
      { name: "phone", label: "Phone", type: "tel", section: "Contact", required: true },
      { name: "child_age", label: "Child age", type: "text", section: "Care", required: true },
      { name: "duration", label: "Duration (hours)", type: "number", section: "Care", required: true, attrs: { min: "1", step: "1" } },
      {
        name: "service_type",
        label: "Service type",
        type: "select",
        section: "Care",
        required: true,
        options: ["Nanny", "Daycare", "Full-time"],
      },
      { name: "start_date", label: "Start date", type: "date", section: "Schedule", required: true },
    ],
  },
  Photographer: {
    title: "Photographer",
    icon: "📷",
    fields: [
      { name: "name", label: "Full name", type: "text", section: "Contact", required: true },
      { name: "phone", label: "Phone", type: "tel", section: "Contact", required: true },
      { name: "event_type", label: "Event type", type: "text", section: "Shoot", required: true },
      { name: "duration", label: "Duration (hours)", type: "number", section: "Shoot", required: true, attrs: { min: "1", step: "1" } },
      { name: "location", label: "Location", type: "text", section: "Shoot", required: true },
      { name: "date", label: "Date", type: "date", section: "Shoot", required: true },
    ],
  },
  "Medical Services": {
    title: "Medical service",
    icon: "🩺",
    fields: [
      { name: "name", label: "Your name", type: "text", section: "Contact", required: true },
      { name: "phone", label: "Phone", type: "tel", section: "Contact", required: true },
      { name: "patient_name", label: "Patient name", type: "text", section: "Medical", required: true },
      {
        name: "service_type",
        label: "Service type",
        type: "select",
        section: "Medical",
        required: true,
        options: ["Doctor Visit", "Nurse Visit", "Lab Test", "Physiotherapy"],
      },
      {
        name: "urgency",
        label: "Urgency level",
        type: "select",
        section: "Medical",
        required: true,
        options: ["Normal", "Priority", "Emergency"],
      },
      { name: "date", label: "Date", type: "date", section: "Medical", required: true },
    ],
  },
  "House Service": {
    title: "House service",
    icon: "🏠",
    fields: [
      { name: "name", label: "Full name", type: "text", section: "Contact", required: true },
      { name: "phone", label: "Phone", type: "tel", section: "Contact", required: true },
      {
        name: "service_type",
        label: "Service type",
        type: "select",
        section: "Job",
        required: true,
        options: ["Deep clean", "Repair visit", "Pest control", "Other"],
      },
      { name: "address", label: "Address", type: "text", section: "Job", required: true },
      { name: "preferred_time", label: "Preferred time", type: "time", section: "Job", required: true },
    ],
  },
};

export function getCategoryConfig(categoryName) {
  const k = (categoryName || "").trim();
  if (CONFIG[k]) return CONFIG[k];
  return CONFIG._default;
}

export function estimateTotal(categoryName, formData, selectedMenu, service) {
  const people = Number(formData?.people || 1) || 1;
  if (categoryName === "Catering" && selectedMenu?.price != null) {
    return Math.round(Number(selectedMenu.price) * people);
  }
  const base = Number(service?.price) || 499;
  return Math.round(Math.max(499, base));
}

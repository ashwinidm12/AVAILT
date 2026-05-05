/**
 * Category-specific booking form definitions.
 * Plain script (no bundler): use window.AvailtCategoryConfig.get(categoryName)
 */
(function (global) {
  var categoryConfig = {
    _default: {
      title: "Book service",
      icon: "📋",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Your details",
          required: true,
          placeholder: "Full name",
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Your details",
          required: true,
          placeholder: "+91 …",
        },
        {
          name: "notes",
          label: "Notes",
          type: "text",
          section: "Booking",
          required: false,
          placeholder: "Anything we should know?",
        },
        {
          name: "date",
          label: "Preferred date",
          type: "date",
          section: "Booking",
          required: true,
        },
      ],
    },

    Catering: {
      title: "Catering service",
      icon: "🍽️",
      extra: "menu",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "people",
          label: "Number of people",
          type: "number",
          section: "Event details",
          required: true,
          attrs: { min: "1", step: "1", value: "2" },
        },
        {
          name: "event",
          label: "Event type",
          type: "text",
          section: "Event details",
          required: true,
          placeholder: "Wedding, corporate lunch…",
        },
        {
          name: "date",
          label: "Event date",
          type: "date",
          section: "Event details",
          required: true,
        },
      ],
    },

    "Baby Care": {
      title: "Baby care service",
      icon: "👶",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "child_age",
          label: "Child age (years)",
          type: "number",
          section: "Child info",
          required: true,
          attrs: { min: "0", step: "0.5", value: "2" },
        },
        {
          name: "duration",
          label: "Duration (hours)",
          type: "number",
          section: "Child info",
          required: true,
          attrs: { min: "1", step: "1", value: "4" },
        },
        {
          name: "service_type",
          label: "Service type",
          type: "select",
          section: "Child info",
          required: true,
          options: ["Nanny", "Daycare", "Full-time"],
        },
        {
          name: "start_date",
          label: "Start date",
          type: "date",
          section: "Schedule",
          required: true,
        },
      ],
    },

    Photographer: {
      title: "Photographer booking",
      icon: "📷",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "event_type",
          label: "Event type",
          type: "text",
          section: "Shoot details",
          required: true,
        },
        {
          name: "duration",
          label: "Duration (hours)",
          type: "number",
          section: "Shoot details",
          required: true,
          attrs: { min: "1", step: "1", value: "4" },
        },
        {
          name: "location",
          label: "Location / venue",
          type: "text",
          section: "Shoot details",
          required: true,
        },
        {
          name: "date",
          label: "Date",
          type: "date",
          section: "Shoot details",
          required: true,
        },
      ],
    },

    "Venue Booking": {
      title: "Venue booking",
      icon: "🏛️",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "guests",
          label: "Expected guests",
          type: "number",
          section: "Event details",
          required: true,
          attrs: { min: "1", step: "1", value: "100" },
        },
        {
          name: "event",
          label: "Event name",
          type: "text",
          section: "Event details",
          required: true,
        },
        {
          name: "date",
          label: "Event date",
          type: "date",
          section: "Event details",
          required: true,
        },
      ],
    },

    "Event Management": {
      title: "Event management",
      icon: "🎉",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "company",
          label: "Company / host",
          type: "text",
          section: "Event",
          required: false,
        },
        {
          name: "event",
          label: "Event name",
          type: "text",
          section: "Event",
          required: true,
        },
        {
          name: "attendees",
          label: "Approx. attendees",
          type: "number",
          section: "Event",
          required: true,
          attrs: { min: "1", step: "1", value: "50" },
        },
        {
          name: "date",
          label: "Event date",
          type: "date",
          section: "Event",
          required: true,
        },
      ],
    },

    "Medical Services": {
      title: "Medical service",
      icon: "🩺",
      fields: [
        {
          name: "name",
          label: "Patient / contact name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "address",
          label: "Visit address",
          type: "text",
          section: "Visit",
          required: true,
        },
        {
          name: "symptoms",
          label: "Reason for visit",
          type: "text",
          section: "Visit",
          required: false,
        },
        {
          name: "date",
          label: "Preferred date",
          type: "date",
          section: "Visit",
          required: true,
        },
      ],
    },

    "Blood Donors": {
      title: "Blood donor service",
      icon: "🩸",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "blood_group",
          label: "Blood group needed",
          type: "select",
          section: "Request",
          required: true,
          options: ["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-", "Any"],
        },
        {
          name: "urgency",
          label: "Urgency",
          type: "select",
          section: "Request",
          required: true,
          options: ["Standard", "Within 24h", "Emergency"],
        },
        {
          name: "date",
          label: "Needed by",
          type: "date",
          section: "Request",
          required: true,
        },
      ],
    },

    Tutoring: {
      title: "Tutoring",
      icon: "📚",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "subject",
          label: "Subject / level",
          type: "text",
          section: "Session",
          required: true,
        },
        {
          name: "sessions",
          label: "Sessions per week",
          type: "number",
          section: "Session",
          required: true,
          attrs: { min: "1", step: "1", value: "2" },
        },
        {
          name: "date",
          label: "Start date",
          type: "date",
          section: "Session",
          required: true,
        },
      ],
    },

    "Pet Store": {
      title: "Pet store",
      icon: "🐾",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "pet_type",
          label: "Pet type",
          type: "text",
          section: "Order",
          required: true,
        },
        {
          name: "delivery",
          label: "Delivery address",
          type: "text",
          section: "Order",
          required: true,
        },
        {
          name: "date",
          label: "Delivery date",
          type: "date",
          section: "Order",
          required: true,
        },
      ],
    },

    "Farmers Services": {
      title: "Farmers services",
      icon: "🌾",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "farm_size",
          label: "Farm size (acres)",
          type: "number",
          section: "Farm",
          required: true,
          attrs: { min: "0.1", step: "0.1", value: "1" },
        },
        {
          name: "service_need",
          label: "Service needed",
          type: "select",
          section: "Farm",
          required: true,
          options: ["Soil audit", "Harvest assist", "Crop advisory", "Other"],
        },
        {
          name: "date",
          label: "Preferred date",
          type: "date",
          section: "Farm",
          required: true,
        },
      ],
    },

    "Grocery Store": {
      title: "Grocery delivery",
      icon: "🛒",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "address",
          label: "Delivery address",
          type: "text",
          section: "Delivery",
          required: true,
        },
        {
          name: "window",
          label: "Delivery window",
          type: "select",
          section: "Delivery",
          required: true,
          options: ["9am–12pm", "12pm–4pm", "4pm–8pm"],
        },
        {
          name: "date",
          label: "Delivery date",
          type: "date",
          section: "Delivery",
          required: true,
        },
      ],
    },

    Boutique: {
      title: "Boutique",
      icon: "👗",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "occasion",
          label: "Occasion",
          type: "text",
          section: "Styling",
          required: false,
        },
        {
          name: "size",
          label: "Size / notes",
          type: "text",
          section: "Styling",
          required: false,
        },
        {
          name: "date",
          label: "Appointment date",
          type: "date",
          section: "Styling",
          required: true,
        },
      ],
    },

    "Book Mart": {
      title: "Book mart",
      icon: "📖",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "titles",
          label: "Titles / topics",
          type: "text",
          section: "Order",
          required: true,
        },
        {
          name: "address",
          label: "Shipping address",
          type: "text",
          section: "Order",
          required: true,
        },
        {
          name: "date",
          label: "Needed by",
          type: "date",
          section: "Order",
          required: true,
        },
      ],
    },

    "House Service": {
      title: "House service",
      icon: "🏠",
      fields: [
        {
          name: "name",
          label: "Full name",
          type: "text",
          section: "Contact",
          required: true,
        },
        {
          name: "phone",
          label: "Phone",
          type: "tel",
          section: "Contact",
          required: true,
        },
        {
          name: "address",
          label: "Property address",
          type: "text",
          section: "Job",
          required: true,
        },
        {
          name: "job_type",
          label: "Job type",
          type: "select",
          section: "Job",
          required: true,
          options: ["Deep clean", "Repair visit", "Pest control", "Other"],
        },
        {
          name: "date",
          label: "Service date",
          type: "date",
          section: "Job",
          required: true,
        },
      ],
    },
  };

  function get(categoryName) {
    var key = (categoryName || "").trim();
    if (key && categoryConfig[key]) {
      return categoryConfig[key];
    }
    return categoryConfig._default;
  }

  global.AvailtCategoryConfig = {
    config: categoryConfig,
    get: get,
  };
})(window);

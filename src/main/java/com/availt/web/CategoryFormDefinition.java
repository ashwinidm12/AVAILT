package com.availt.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Category-specific booking fields. Catering block matches production catering flow exactly.
 */
public final class CategoryFormDefinition {

    public static final class Field {
        private final String name;
        private final String label;
        private final String inputType;
        private final boolean required;
        private final int textareaRows;

        public Field(String name, String label, String inputType, boolean required) {
            this(name, label, inputType, required, 0);
        }

        public Field(String name, String label, String inputType, boolean required, int textareaRows) {
            this.name = name;
            this.label = label;
            this.inputType = inputType;
            this.required = required;
            this.textareaRows = textareaRows;
        }

        public String getName() {
            return name;
        }

        public String getLabel() {
            return label;
        }

        public String getInputType() {
            return inputType;
        }

        public boolean isRequired() {
            return required;
        }

        public int getTextareaRows() {
            return textareaRows > 0 ? textareaRows : 4;
        }
    }

    private CategoryFormDefinition() {
    }

    public static String normalizeCategoryKey(String category) {
        if (category == null) {
            return "_default";
        }
        String c = category.trim();
        if (c.isEmpty()) {
            return "_default";
        }
        if (c.equalsIgnoreCase("Catering")) {
            return "Catering";
        }
        if (c.equalsIgnoreCase("Venue Booking")) {
            return "Venue Booking";
        }
        if (c.equalsIgnoreCase("Event Management")) {
            return "Event Management";
        }
        if (c.equalsIgnoreCase("Photographer")) {
            return "Photographer";
        }
        if (c.equalsIgnoreCase("Medical Services")) {
            return "Medical Services";
        }
        if (c.equalsIgnoreCase("Blood Donors")) {
            return "Blood Donors";
        }
        if (c.equalsIgnoreCase("Baby Care")) {
            return "Baby Care";
        }
        if (c.equalsIgnoreCase("Tutoring")) {
            return "Tutoring";
        }
        if (c.equalsIgnoreCase("Pet Store")) {
            return "Pet Store";
        }
        if (c.equalsIgnoreCase("Farmers Services")) {
            return "Farmers Services";
        }
        if (c.equalsIgnoreCase("Grocery Store")) {
            return "Grocery Store";
        }
        if (c.equalsIgnoreCase("Boutique")) {
            return "Boutique";
        }
        if (c.equalsIgnoreCase("Book Mart")) {
            return "Book Mart";
        }
        if (c.equalsIgnoreCase("House Service") || c.equalsIgnoreCase("House Services")) {
            return "House Service";
        }
        return "_default";
    }

    public static List<Field> fieldsFor(String category) {
        String key = normalizeCategoryKey(category);
        if ("Catering".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("people", "Number of people", "number", true),
                    f("eventType", "Event type", "text", true),
                    f("date", "Event date", "date", true)
            );
        }
        if ("Venue Booking".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("venueType", "Venue type", "text", true),
                    f("capacity", "Expected guest capacity", "number", true),
                    f("eventDate", "Event date", "date", true),
                    f("timeSlot", "Start time", "time", true),
                    f("location", "Venue / area preference", "text", true),
                    f("parkingNeeded", "Parking needs (e.g. valet, self)", "text", true),
                    tf("decorationNeeds", "Decoration & AV needs", 4, true)
            );
        }
        if ("Event Management".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("eventType", "Event type", "text", true),
                    f("guestCount", "Approximate guest count", "number", true),
                    f("budget", "Budget range (INR)", "text", true),
                    f("venueNote", "Venue / location notes", "text", true),
                    tf("servicesNeeded", "Services needed (decor, artists, logistics)", 4, true),
                    f("eventDate", "Event date", "date", true),
                    f("timeSlot", "Preferred time", "time", true)
            );
        }
        if ("Photographer".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("shootType", "Shoot type (wedding, product, portrait…)", "text", true),
                    f("eventType", "Event / brief", "text", true),
                    f("shootDate", "Shoot date", "date", true),
                    f("timeSlot", "Preferred time", "time", true),
                    f("location", "Location / venue", "text", true),
                    f("packageChoice", "Package (hours, deliverables)", "text", true),
                    tf("albumNeeds", "Album & editing needs", 3, false)
            );
        }
        if ("Medical Services".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("patientName", "Patient name", "text", true),
                    tf("issueDescription", "Issue / symptoms summary", 3, true),
                    f("serviceNeeded", "Service needed (visit, lab, tele)", "text", true),
                    f("visitDate", "Preferred date", "date", true),
                    f("timeSlot", "Preferred time", "time", true),
                    f("visitAddress", "Visit address", "text", true),
                    f("urgency", "Urgency (routine, same-day, emergency)", "text", true)
            );
        }
        if ("Blood Donors".equals(key)) {
            return list(
                    f("requesterName", "Requester name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("bloodGroup", "Blood group needed", "text", true),
                    f("hospitalName", "Hospital / facility", "text", true),
                    f("patientName", "Patient name (or ID ref)", "text", true),
                    f("location", "City / area", "text", true),
                    f("requiredDate", "Required by date", "date", true),
                    f("urgency", "Urgency", "text", true)
            );
        }
        if ("Baby Care".equals(key)) {
            return list(
                    f("parentName", "Parent / guardian name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("childAge", "Child age", "text", true),
                    f("serviceDuration", "Service duration", "text", true),
                    f("careType", "Care type (nanny, daycare, night nurse…)", "text", true),
                    f("serviceAddress", "Service address", "text", true),
                    f("preferredTime", "Preferred time window", "text", true),
                    tf("additionalNotes", "Additional notes", 3, false)
            );
        }
        if ("Tutoring".equals(key)) {
            return list(
                    f("parentOrStudentName", "Parent / student name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("subject", "Subject", "text", true),
                    f("classGrade", "Class / grade", "text", true),
                    f("mode", "Mode (online, at-home, centre)", "text", true),
                    f("durationHours", "Session length (hours)", "text", true),
                    f("studentName", "Student name (if different)", "text", false),
                    tf("scheduleNotes", "Schedule preference", 3, true),
                    f("startDate", "Start date", "date", true)
            );
        }
        if ("Pet Store".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("petType", "Pet type", "text", true),
                    tf("itemsNeeded", "Items needed", 3, true),
                    f("quantity", "Quantity / pack size", "text", true),
                    f("deliveryAddress", "Delivery address", "text", true),
                    f("deliverySlot", "Delivery slot", "text", true)
            );
        }
        if ("Farmers Services".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("cropServiceType", "Crop / service type", "text", true),
                    f("landLocation", "Farm / land location", "text", true),
                    f("quantityOrArea", "Quantity or land area", "text", true),
                    f("purpose", "Purpose (soil test, harvest, advisory…)", "text", true),
                    f("serviceDate", "Preferred service date", "date", true)
            );
        }
        if ("Grocery Store".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    tf("itemList", "Item list", 4, true),
                    f("quantitySummary", "Quantity summary", "text", true),
                    f("deliveryAddress", "Delivery address", "text", true),
                    f("deliverySlot", "Delivery slot", "text", true),
                    f("paymentPreference", "Payment preference", "text", true)
            );
        }
        if ("Boutique".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("occasion", "Occasion", "text", true),
                    f("dressType", "Dress type / style", "text", true),
                    f("size", "Size", "text", true),
                    f("budget", "Budget (INR)", "text", true),
                    f("appointmentDate", "Appointment / visit date", "date", true),
                    f("visitLocation", "Visit location / store", "text", true)
            );
        }
        if ("Book Mart".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("bookCategory", "Book category", "text", true),
                    f("bookTitle", "Title / ISBN", "text", true),
                    f("quantity", "Quantity", "number", true),
                    f("classLevel", "Class / level (if academic)", "text", false),
                    f("deliveryAddress", "Delivery address", "text", true)
            );
        }
        if ("House Service".equals(key)) {
            return list(
                    f("customerName", "Your name", "text", true),
                    f("phone", "Phone", "tel", true),
                    f("serviceType", "Service type (cleaning, plumbing…)", "text", true),
                    tf("issueDetails", "Issue details", 4, true),
                    f("address", "Service address", "text", true),
                    f("serviceDate", "Preferred date", "date", true),
                    f("timePreference", "Preferred time", "text", true),
                    f("urgency", "Urgency", "text", true)
            );
        }
        return list(
                f("customerName", "Your name", "text", true),
                f("phone", "Phone", "tel", true),
                tfo("notes", "Notes", 3, false),
                f("date", "Preferred date", "date", true)
        );
    }

    private static Field f(String name, String label, String type, boolean required) {
        return new Field(name, label, type, required);
    }

    private static Field tf(String name, String label, int rows, boolean required) {
        return new Field(name, label, "textarea", required, rows);
    }

    private static Field tfo(String name, String label, int rows, boolean required) {
        return new Field(name, label, "textarea", required, rows);
    }

    private static List<Field> list(Field... fields) {
        List<Field> out = new ArrayList<Field>();
        Collections.addAll(out, fields);
        return out;
    }

    public static Map<String, String> extractData(String category, Map<String, String[]> parameterMap) {
        Map<String, String> out = new LinkedHashMap<String, String>();
        for (Field field : fieldsFor(category)) {
            String[] vals = parameterMap.get(field.getName());
            String v = vals == null || vals.length == 0 ? "" : vals[0].trim();
            if (v.isEmpty() && !field.isRequired()) {
                continue;
            }
            out.put(field.getName(), v);
        }
        return out;
    }

    public static List<String> validate(String category, Map<String, String> data) {
        List<String> errors = new ArrayList<String>();
        for (Field field : fieldsFor(category)) {
            if (!field.isRequired()) {
                continue;
            }
            String v = data.get(field.getName());
            if (v == null || v.trim().isEmpty()) {
                errors.add(field.getLabel() + " is required");
            }
        }
        String key = normalizeCategoryKey(category);
        if ("Catering".equals(key)) {
            String people = data.get("people");
            if (people != null && !people.isEmpty()) {
                try {
                    int p = Integer.parseInt(people);
                    if (p < 1) {
                        errors.add("Number of people must be at least 1");
                    }
                } catch (NumberFormatException e) {
                    errors.add("Number of people must be a valid number");
                }
            }
        }
        if ("Venue Booking".equals(key)) {
            parsePositiveInt(data.get("capacity"), "Expected guest capacity", errors);
        }
        if ("Event Management".equals(key)) {
            parsePositiveInt(data.get("guestCount"), "Approximate guest count", errors);
        }
        if ("Book Mart".equals(key)) {
            String q = data.get("quantity");
            if (q != null && !q.trim().isEmpty()) {
                try {
                    int n = Integer.parseInt(q.trim());
                    if (n < 1) {
                        errors.add("Quantity must be at least 1");
                    }
                } catch (NumberFormatException e) {
                    errors.add("Quantity must be a valid number");
                }
            }
        }
        return errors;
    }

    private static void parsePositiveInt(String raw, String label, List<String> errors) {
        if (raw == null || raw.trim().isEmpty()) {
            return;
        }
        try {
            int n = Integer.parseInt(raw.trim());
            if (n < 1) {
                errors.add(label + " must be at least 1");
            }
        } catch (NumberFormatException e) {
            errors.add(label + " must be a valid number");
        }
    }

    public static boolean isCatering(String category) {
        return "Catering".equalsIgnoreCase(category == null ? "" : category.trim());
    }

    public static Map<String, Object> toBookingDataJsonMap(String category, Map<String, String> data) {
        Map<String, Object> m = new LinkedHashMap<String, Object>();
        String key = normalizeCategoryKey(category);
        for (Map.Entry<String, String> e : data.entrySet()) {
            if ("people".equals(e.getKey()) && "Catering".equals(key)) {
                try {
                    m.put("people", Integer.parseInt(e.getValue()));
                } catch (NumberFormatException ex) {
                    m.put("people", e.getValue());
                }
            } else if (isWholeNumberField(key, e.getKey())) {
                try {
                    m.put(e.getKey(), Integer.parseInt(e.getValue().trim()));
                } catch (NumberFormatException ex) {
                    m.put(e.getKey(), e.getValue());
                }
            } else {
                m.put(e.getKey(), e.getValue());
            }
        }
        return m;
    }

    private static boolean isWholeNumberField(String categoryKey, String fieldName) {
        if ("Venue Booking".equals(categoryKey) && "capacity".equals(fieldName)) {
            return true;
        }
        if ("Event Management".equals(categoryKey) && "guestCount".equals(fieldName)) {
            return true;
        }
        if ("Book Mart".equals(categoryKey) && "quantity".equals(fieldName)) {
            return true;
        }
        return false;
    }

    public static String categoryLabel(String category) {
        if (category == null) {
            return "Service";
        }
        return category.trim();
    }

    public static boolean categoryNeedsMenuStep(String category) {
        return isCatering(category);
    }

    public static String safeCategoryForCompare(String category) {
        return category == null ? "" : category.trim().toLowerCase(Locale.ROOT);
    }
}

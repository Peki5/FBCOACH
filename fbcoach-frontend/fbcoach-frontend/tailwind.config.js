/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        "fbcoach-bg": "#0f172a",
        "fbcoach-surface": "#1e293b",
        "fbcoach-surface-light": "#334155",
        "fbcoach-primary": "#10b981",
        "fbcoach-primary-hover": "#059669",
        "fbcoach-text": "#f1f5f9",
        "fbcoach-text-muted": "#94a3b8",
        "fbcoach-border": "#334155",
        "fbcoach-danger": "#ef4444",
        "fbcoach-danger-hover": "#dc2626",
        "fbcoach-warning": "#f59e0b",
        "fbcoach-warning-hover": "#d97706",
        "fbcoach-info": "#3b82f6",
        "fbcoach-info-hover": "#2563eb",
      }
    },
    fontFamily: {
      Roboto: ["Roboto, sans-serif"],
    },
    container: {
      padding: "2rem",
      center: true,
    },
    screens: {
      sm: "640px",
      md: "768px",
      lg: "1024px",
    }
  },
  plugins: [],
}

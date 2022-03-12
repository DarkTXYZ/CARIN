module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      backgroundImage: theme => ({
        'BG': "url('./lib/DarkSunrise.jpg')"
      }),
    },
  },
  plugins: [],
}

const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

const foodRoute = require('./Routes/Food');
const waterRoute = require('./Routes/Water');

app.use(foodRoute);
app.use(waterRoute);


const PORT = 5000;
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});


module.exports = app;
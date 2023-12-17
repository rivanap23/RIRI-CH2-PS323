const app = require('./Routes/userRoutes');
const HOST ='0.0.0.0';


const PORT = process.env.PORT || 8000;
app.listen(PORT, () => {
  console.log(`Running on http://${HOST}:${PORT}`);
});

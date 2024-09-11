const express = require('express');
const path = require('path');

const app = express();

app.use(express.json());
app.use(express.static(path.join(__dirname, 'static')))

app.listen(80, () =>
{
    console.log('Succesfully started');
});
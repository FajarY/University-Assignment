const express = require('express');
const app = express();
const path = require('path');

app.use(express.json());
app.use(express.static(path.join(__dirname, '../static')));

app.listen(80, () =>
{
    console.log('Succesfully bind server');
});
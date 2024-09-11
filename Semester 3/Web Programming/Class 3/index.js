const express = require('express');
const app = express();

app.use(express.json());
app.use(express.static(require('path').join(__dirname, 'static')));

app.listen(80, () =>
{
    console.log('Succeed');
})
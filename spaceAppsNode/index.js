const express = require('express');
const cookieParser = require('cookie-parser');
const helmet = require('helmet');
const path = require('path');
const morgan = require('morgan');
const fs = require('fs');
const hbs = require('hbs');

const {disaster} = require('./db_models/disaster');

var logStream = fs.createWriteStream(path.join(__dirname, 'access.log'), { flags: 'a' });
var app = express();

var gobag = require('./routes/gobag');
var register = require('./routes/register');
var login = require('./routes/login');
var location = require('./routes/location');
// var profile = require('./routes/profile');
var admin = require('./routes/admin');

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');

app.use(express.json());
app.use(morgan('combined', {stream: logStream}));
app.use(morgan('dev'));
app.use(express.urlencoded({extended: false}));
app.use(cookieParser());
app.use(express.static('media/uploads/'));
// app.use(express.static(path.join(__dirname, 'media')));

app.use('/register', register);
app.use('/login', login);
app.use('/location', location);
app.use('/gobag', gobag);
// app.use('/profile', profile);
app.use('/admin', admin)

app.get('/disasterform', (req, res, next) => {
  res.render('disaster.hbs');
});

app.get('/disaster', (req, res, next)=>{
  var doc = disaster.find().then((doc) => {
    res.json({doc});
  })
  .catch((e) => {
    res.status(400).send(e);
  })
});

module.exports = app;

app.listen(3000, '172.22.0.37', () => {
  console.log('Server started at port 3000 successfully');
});

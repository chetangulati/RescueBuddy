const {mongoose} = require('./../db/mongoose');
const {disaster} = require('./disaster');

var locSchema = mongoose.Schema({
  stateName:{
    type: String,
    required: true,
    minlength: 1
  },
  disasters:[{
      type: String,
      required: true,
    }],
  contacts:{
    police: {
      type: Number,
      required: true,
      minlength: 1
    },
    ambulance:{
      type: Number,
      required: true,
      minlength: 1
    },
    fire:{
      type:Number,
      required: true,
      minlength: 1
    }
  }
});

var location = mongoose.model('location', locSchema);
module.exports = {location};

const {mongoose} = require('./../db/mongoose');

var itemSchema = mongoose.Schema({
  item_name: {
    type: String,
    required: true,
    minlength: 1
  },
  item_wt:{
    type: Number,
    required: true,
    minlength: 1
  },
  item_desc:{
    type: String,
    required: true,
    minlength: 1
  },
  item_qty_min:{
    type: Number,
    required: true,
    minlength: 1
  },
  item_qty_max:{
    type: Number,
    required: true,
    minlength: 1
  },
  item_exp_date:{
    type: Number,
    required: true,
  }
});


var item = mongoose.model('item', itemSchema);
module.exports = {item};

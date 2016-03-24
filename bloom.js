var bloom;

jQuery( function () {
  bloomDisplay = d3.select('#bloom')
                .selectAll('div')
                .data(bloom.scalajs.BloomApp().bloom.bloom);
  var bloomEnter = bloomDisplay.enter();

  setColor(bloomEnter.append('div'));

  update();
});

function setColor(sel) {
  // I should probably use css classes to make these alternations, but for now it's manual
  sel.transition().duration(800).style('background-color', function(d) {
    if ((d & bloom.scalajs.Bloom().SET) != 0) {
      return 'steelblue';
    } else {
      return 'lightsteelblue';
    }
  }).style('border-color', function(d) {
    if ((d & bloom.scalajs.Bloom().CHECKED) != 0) {
      return 'firebrick';
    } else {
      return 'white';
    }
  });
}

function update() {
  // I don't think I'm supposed to re-join here, but I haven't figured out how else to get this to work
  setColor(bloomDisplay.data(bloom.scalajs.BloomApp().bloom.bloom));
}

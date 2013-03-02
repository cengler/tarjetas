# FUNCIONES
drawChart = (dataV) ->
  #alert [['A',10],['B',1],['A',10],['C',2],['D',3],['E',5]]
  #data = google.visualization.arrayToDataTable([['A',10],['B',1],['A',10],['C',2],['D',3],['E',5]]);
  #data = google.visualization.arrayToDataTable(dataGet);
  options = {title: 'Gastos por Categoria',pieResidueSliceLabel: 'Otros'};
  chart = new google.visualization.PieChart(document.getElementById('chart_div'));
  chart.draw(dataV, options);
  0

google.load("visualization", "1",
  packages:["corechart"]
);

$ ->
  $.get "/category/pie", (data) ->
    map = new google.visualization.DataTable();
    map.addRows(data.length);
    map.addColumn('string', 'Categoria')
    map.addColumn('number', 'Monto')
    $.each data, (i, v) ->
      map.setValue(i, 0, v.key)
      map.setValue(i, 1, v.value)
    google.setOnLoadCallback(drawChart(map));
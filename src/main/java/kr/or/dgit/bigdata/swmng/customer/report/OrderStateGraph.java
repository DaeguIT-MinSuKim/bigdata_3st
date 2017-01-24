package kr.or.dgit.bigdata.swmng.customer.report;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.util.CreateDatabaseAuto;

@SuppressWarnings("serial")
public class OrderStateGraph extends JPanel {

	private static List<Map<String, Object>> listmapForbc;
	private static List<Map<String, Object>> listmapForPie;
	private static BarChart<String, Number> bc;
	private static NumberAxis yAxis;
	private static CategoryAxis xAxis;

	public OrderStateGraph() {
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));

		JFXPanel fxPanel = new JFXPanel();
		fxPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
		add(fxPanel, BorderLayout.CENTER);
		fxPanel.setBackground(new java.awt.Color(255, 255, 255));
		JPanel pnBtn = new JPanel();
		add(pnBtn, BorderLayout.NORTH);
		pnBtn.setLayout(new BorderLayout(0, 0));

		Platform.runLater(new Runnable() {
			public void run() {
				initFX(fxPanel);
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void initFX(JFXPanel fxPanel) {
		// This method is invoked on the JavaFX thread
		AnchorPane anchorPane = new AnchorPane();

		Scene scene = new Scene(anchorPane, Color.BEIGE);
		VBox box = new VBox();
		box.prefWidthProperty().bind(anchorPane.widthProperty());
		box.prefHeightProperty().bind(anchorPane.heightProperty());
		box.setStyle("-fx-alignment:center;");

		yAxis = new NumberAxis();
		xAxis = new CategoryAxis();
		bc = new BarChart<String, Number>(xAxis, yAxis);
		PieChart pie = new PieChart();
		box.getChildren().addAll(bc, pie);
		anchorPane.getChildren().addAll(box);

		bc.setTitle("고객별 주문수량");
		yAxis.setLabel("주문수량");
		xAxis.setLabel("상호명");

		try {
			listmapForbc = SaleService.getInstance().selectAllGroupByConame();
		} catch (Exception e) {
			if (JOptionPane.showConfirmDialog(null, "데이터가 없습니다. 초기화 하시겠습니까?") == 0) {
				new CreateDatabaseAuto().resetDB();
				listmapForbc = SaleService.getInstance().selectAllGroupByConame();
			} else {
				System.exit(0);
			}

		}

		listmapForPie = SaleService.getInstance().selectSalesOfEach();
		XYChart.Series series1 = new XYChart.Series();

		for (int i = 0; i < listmapForbc.size(); i++) {
			Map<String, Object> tempMap = listmapForbc.get(i);
			series1.getData().add(new XYChart.Data(tempMap.get("shopName"), tempMap.get("totalCnt")));
		}

		bc.getData().addAll(series1);

		BigDecimal result = BigDecimal.valueOf(0);
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

		for (int i = 0; i < listmapForPie.size(); i++) {

			Map<String, Object> tempMap = listmapForPie.get(i);
			BigDecimal bd = (BigDecimal) tempMap.get("result");
			result = result.add(bd);
		}

		for (int i = 0; i < listmapForPie.size(); i++) {

			Map<String, Object> tempMap = listmapForPie.get(i);
			BigDecimal bd = (BigDecimal) tempMap.get("result");
			BigDecimal ratio = bd.divide(result, 3, BigDecimal.ROUND_UP);
			double temp = ratio.multiply(new BigDecimal("100")).doubleValue();
			pieChartData.add(new PieChart.Data((String) tempMap.get("shopName"), temp));
		}

		pieChartData.forEach(
				data -> data.nameProperty().bind(Bindings.concat(data.getName(), " ", data.pieValueProperty(), " %")));

		pie.setData(pieChartData);
		pie.setTitle("매출비율");
		pie.setLegendSide(Side.BOTTOM);
		pie.lookup(".chart-title").setStyle("-fx-font-size: 1.8em");
		pie.lookup(".chart-legend").setStyle("-fx-background-color:  transparent");

		tableDesignSetting();
		fxPanel.setScene(scene);
	}

	private static void tableDesignSetting() {
		for (int i = 0; i < listmapForbc.size(); i++) {
			bc.lookup(".data" + i + ".chart-bar").setStyle("-fx-background-color:#FF0051");
		}

		bc.lookup(".chart-title").setStyle("-fx-font-size: 1.8em");
		bc.setBarGap(0.0);
		bc.setCategoryGap(40);
		bc.setLegendVisible(false);

		xAxis.lookup(".axis-label").setStyle("-fx-label-padding:15 0 10 0");
		xAxis.setTickLength(5);
		xAxis.setTickLabelGap(5);
		xAxis.setTickLabelRotation(15);

	}

}

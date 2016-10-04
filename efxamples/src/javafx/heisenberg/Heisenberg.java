package javafx.heisenberg;

import java.io.File;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Heisenberg extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		TilePane pane = new TilePane(1.0, 1.0);
		pane.setPrefColumns(10);
		Scene scene = new Scene(pane, 800, 800);
		stage.setScene(scene);
		ObservableList<Node> children = pane.getChildren();
		for (int i  = 0; i  < symbols.length; i += 2) {
			children.add(createSymbol(symbols[i + 1], i + 1));
		}

		Button button = new Button("Save");
		button.setOnAction((ActionEvent event) -> {
			for (int i  = 0; i  < symbols.length; i += 2) {
				Node tile = children.get(i / 2);
				WritableImage snapshot = tile.snapshot(new SnapshotParameters(), null);
				File file = new File("/Users/hal/java/git/javafx/efxamples/src/javafx/heisenberg/", symbols[i + 1] + ".png");
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
				} catch (Exception e) {
				}
			}
		});
		children.add(button);
		stage.show();
	}

	private int size = 52;
	double textOffset = 8.0;
	double numOffset = 2.0;
	double textSize = 36;
	double numSize = 12;
	
	private Node createSymbol(String symbol, int num) {
		StackPane pane = new StackPane();
		int width = size, height = size;
		pane.setMinSize(width, height);
		pane.setPrefSize(width, height);
		pane.setMaxSize(width, height);
		pane.setStyle("-fx-background-color: green; -fx-font-family: Arial; -fx-border-style: solid; -fx-border-width: 1px");
		Text symbolText = new Text(symbol);
		symbolText.setStyle("-fx-font-size: " + textSize);
		symbolText.setFill(Color.WHITE);
		StackPane.setAlignment(symbolText, Pos.BOTTOM_CENTER);
		Text symbolNum = new Text(String.valueOf(num / 2 + 1)); 
		symbolNum.setStyle("-fx-font-size: " + numSize);
		symbolNum.setFill(Color.WHITE);
		StackPane.setAlignment(symbolNum, Pos.TOP_RIGHT);
		StackPane.setMargin(symbolNum, new Insets(2.0, 2.0, 0.0, 0.0));
		pane.getChildren().addAll(symbolText, symbolNum);
		
		return pane;
	}
	
	public static void main(String[] args) {
		launch(Heisenberg.class, args);
	}
	
	protected String[] symbols = {
		"Hydrogen", 	"H",
		"Helium", 	"He",
		"Lithium", 	"Li",
		"Beryllium", 	"Be",
		"Boron", 	"B",
		"Carbon", 	"C",
		"Nitrogen", 	"N",
		"Oxygen", 	"O",
		"Fluorine", 	"F",
		"Neon", 	"Ne",
		"Sodium", 	"Na",
		"Magnesium", 	"Mg",
		"Aluminum", 	"Al",
		"Silicon", 	"Si",
		"Phosphorus", 	"P",
		"Sulfur", 	"S",
		"Chlorine", 	"Cl",
		"Argon", 	"Ar",
		"Potassium", 	"K",
		"Calcium", 	"Ca",
		"Scandium", 	"Sc",
		"Titanium", 	"Ti",
		"Vanadium", 	"V",
		"Chromium", 	"Cr",
		"Manganese", 	"Mn",
		"Iron", 	"Fe",
		"Cobalt", 	"Co",
		"Nickel", 	"Ni",
		"Copper", 	"Cu",
		"Zinc", 	"Zn",
		"Gallium", 	"Ga",
		"Germanium", 	"Ge",
		"Arsenic", 	"As",
		"Selenium", 	"Se",
		"Bromine", 	"Br",
		"Krypton", 	"Kr",
		"Rubidium", 	"Rb",
		"Strontium", 	"Sr",
		"Yttrium", 	"Y",
		"Zirconium", 	"Zr",
		"Niobium", 	"Nb",
		"Molybdenum", 	"Mo",
		"Technetium", 	"Tc",
		"Ruthenium", 	"Ru",
		"Rhodium", 	"Rh",
		"Palladium", 	"Pd",
		"Silver", 	"Ag",
		"Cadmium", 	"Cd",
		"Indium", 	"In",
		"Tin", 	"Sn",
		"Antimony", 	"Sb",
		"Tellurium", 	"Te",
		"Iodine", 	"I",
		"Xenon", 	"Xe",
		"Cesium", 	"Cs",
		"Barium", 	"Ba",
		"Lanthanum", 	"La",
		"Cerium", 	"Ce",
		"Praseodymium", 	"Pr",
		"Neodymium", 	"Nd",
		"Promethium", 	"Pm",
		"Samarium", 	"Sm",
		"Europium", 	"Eu",
		"Gadolinium", 	"Gd",
		"Terbium", 	"Tb",
		"Dysprosium", 	"Dy",
		"Holmium", 	"Ho",
		"Erbium", 	"Er",
		"Thulium", 	"Tm",
		"Ytterbium", 	"Yb",
		"Lutetium", 	"Lu",
		"Hafnium", 	"Hf",
		"Tantalum", 	"Ta",
		"Tungsten", 	"W",
		"Rhenium", 	"Re",
		"Osmium", 	"Os",
		"Iridium", 	"Ir",
		"Platinum", 	"Pt",
		"Gold", 	"Au",
		"Mercury", 	"Hg",
		"Thallium", 	"Tl",
		"Lead", 	"Pb",
		"Bismuth", 	"Bi",
		"Polonium", 	"Po",
		"Astatine", 	"At",
		"Radon", 	"Rn",
		"Francium", 	"Fr",
		"Radium", 	"Ra",
		"Actinium", 	"Ac",
		"Thorium", 	"Th",
		"Protactinium", 	"Pa",
		"Uranium", 	"U",
		"Neptunium", 	"Np",
		"Plutonium", 	"Pu",
		"Americium", 	"Am",
		"Curium", 	"Cm",
		"Berkelium", 	"Bk",
		"Californium", 	"Cf",
		"Einsteinium", 	"Es",
		"Fermium", 	"Fm",
		"Mendelevium", 	"Md",
		"Nobelium", 	"No",
		"Lawrencium", 	"Lr",
		"Rutherfordium", 	"Rf",
		"Dubnium", 	"Db",
		"Seaborgium", 	"Sg",
		"Bohrium", 	"Bh",
		"Hassium", 	"Hs",
		"Meitnerium", 	"Mt",
		"Darmstadtium", 	"Ds",
		"Roentgenium", 	"Rg",
//		"Ununbium", 	"Uub",
//		"Ununtrium", 	"Uut",
//		"Ununquadium", 	"Uuq",
//		"Ununpentium", 	"Uup",
//		"Ununhexium", 	"Uuh",
//		"Ununseptium", 	"Uus",
//		"Ununoctium", 	"Uuo"
	};
}
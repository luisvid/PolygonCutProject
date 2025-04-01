# PolygonCutProject

PolygonCutProject demonstrates an algorithm that splits an arbitrary polygon by a line in the plane. The solution is based on the approach described in the article “[Splitting an Arbitrary Polygon by a Line](https://geidav.wordpress.com/2015/03/21/splitting-an-arbitrary-polygon-by-a-line/)”. The images used in the Main class examples are taken from that article.

## Overview

The project includes a Java implementation that:

- **Receives** an arbitrary polygon defined by a sequence of points.
- **Receives** a line defined by two points.
- **Computes** the intersection points between the polygon’s edges and the cutting line.
- **Inserts** these intersection points into the polygon’s vertex list.
- **Splits** the augmented vertex list into one or more sub-polygons resulting from the cut.

## Algorithm Summary

1. **Detecting Intersections:**  
   For each edge of the polygon, the algorithm determines the relative position of its vertices with respect to the cutting line (using the cross product). When a change of side is detected (or when a vertex touches the line), the intersection point is calculated.

2. **Inserting Intersection Points:**  
   The calculated intersection points are inserted into a new list of vertices, preserving the order of the original polygon.

3. **Splitting the Polygon:**  
   Using the positions of the intersection points, the algorithm extracts segments (cycles) that form valid sub-polygons.

4. **Handling Multiple Intersections:**  
   In cases such as concave polygons (e.g., a “C” shape), the line may intersect the polygon multiple times, resulting in more than two sub-polygons.

## Use of ArrayLists and Lists in Polygon.java

The implementation uses Java’s `List` interface (and its implementation `ArrayList`) for the following reasons:

- **Dynamic Sizing:**  
  As intersection points are discovered, they need to be inserted into the existing sequence of vertices. ArrayLists allow dynamic resizing, which is ideal for this purpose.
- **Ease of Insertion:**  
  The order of vertices is critical to forming valid polygons. ArrayLists provide an easy way to insert new elements at specific indices.
- **Standard Collection Interface:**  
  Using the `List` interface allows for flexibility in choosing different implementations if needed in the future.

## Project Structure

```
PolygonCutProject/
├── src/
│   ├── Main.java      // Demonstrates polygon cutting examples.
│   ├── Point.java     // Represents a point in 2D space.
│   ├── Line.java      // Represents a line defined by two distinct points.
│   └── Polygon.java   // Implements the polygon cutting logic.
└── README.md          // Project documentation and build instructions.
```

- **Point.java** – Defines a point in the plane.
- **Line.java** – Defines a line using two distinct points.
- **Polygon.java** – Contains the logic to cut the polygon with a given line.
- **Main.java** – Contains example code that demonstrates:
  - Cutting a rectangle.
  - Cutting a convex polygon (hexagon example).
  - Cutting a concave polygon (a “C” shape), using images similar to those from the article.

## Building and Running the Project

### Using Visual Studio Code

1. **Set Up Your Environment:**

   - Install the JDK (at least Java 8) and [Visual Studio Code](https://code.visualstudio.com/).
   - Install the [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

2. **Open the Project Folder:**

   - Open the `PolygonCutProject` folder in VS Code.

3. **Compile the Project:**

   - Open the integrated terminal and run:
     ```bash
     javac -d bin src/*.java
     ```
   - This compiles the source files and places the class files in the `bin` folder.

4. **Run the Application:**
   - In the terminal, run:
     ```bash
     java -cp bin Main
     ```

## Acknowledgments

This project was inspired by and based on the article “[Splitting an Arbitrary Polygon by a Line](https://geidav.wordpress.com/2015/03/21/splitting-an-arbitrary-polygon-by-a-line/)”. The examples and images used in the Main class are taken directly from the post.

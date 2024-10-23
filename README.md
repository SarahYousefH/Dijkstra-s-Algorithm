
# 📍 Dijkstra's Shortest Path 

## 📌 Project Overview
This project implements Dijkstra's algorithm to efficiently determine the shortest path between cities in the Gaza Strip. This application showcases the practical application of graph theory in solving real-world problems.

## ✨ Features
- **🗺 Interactive Map Interface:** Users can interactively select cities using the mouse and a drop down list, ensuring user-friendly navigation through the graphical interface.
- **🛣 Real-time Route Visualization:** The shortest path is displayed dynamically on the map, providing clear and precise route information.
- **🏙 Supports Extensive City Data:** Capable of handling complex map data, the system includes at least 60 cities from the Gaza Strip and at least 100 edges between them, showcasing robust scalability.

## 📁 File Format
The application uses a detailed and structured format to store map data efficiently:
- **Vertices (Cities):** Each city is listed with an index followed by its geographical coordinates (latitude and longitude).
- **Edges (Roads):** Roads are represented as pairs of vertices. This structure allows for an accurate representation of real-world distances and connectivity.
- These city and intersection points have been manually collected and accurately placed on the map using graphical scaling, to develop a tailored project.

The file begins with the first line specifying the total number of vertices (cities) followed by the number of edges (roads), establishing the framework of the graph. Subsequent lines list each vertex with its corresponding latitude and longitude, while edges are detailed as pairs of vertex indices, allowing for precise navigation and route optimization.
Example of file content:
```
6 9
City1 31.52583 34.45250
City2 31.53389 35.09944
...
City1 City2
City1 City4
```

## 🧭 Algorithm
### Dijkstra's Algorithm
- **Goal:** To optimize the algorithm for rapid processing of numerous shortest path queries without excessive memory use.
- **Optimization Strategy:** Reduces the number of vertices examined by halting the search as soon as the destination is reached, significantly lowering computational costs.
  
### Time Complexity
The optimized time complexity for processing a shortest path query is approximately \(O(E' \log V')\), where \(E'\) and \(V'\) refer to the edges and vertices examined during the query.

## 🚀 How to Use
1. **Load the Map:** Begin by loading the map data from the specified file format.
2. **Select Cities:** Use the interactive map to choose the starting and destination cities.
3. **View the Route:** The application will display the shortest route on the map in real-time.

## 📥 Input and Output
- **Input:** File containing structured city and road data as described.
- **Output:** Visual representation of the shortest route on the interactive map.


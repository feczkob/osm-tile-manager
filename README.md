# OSM Tile Manager

OSM Tile Manager is a Kotlin application that allows you to download map tiles from a specified area and range of zoom levels as `.png` pictures. 
The downloaded tiles follow the `/zoom/x/y` convention, making them compatible with various mapping libraries and applications (e.g. [MapCompose](https://github.com/p-lr/MapCompose)).

Please consider the [OpenStreetMap tile usage policy](https://operations.osmfoundation.org/policies/tiles/) and the 
[OSM Wiki Downloading data](https://wiki.openstreetmap.org/wiki/Downloading_data) before using this application.
Download the tiles responsibly and avoid overloading the OSM servers.

## Features

- Download map tiles from a specified area with a range of zoom levels
- Rename downloaded directories and tiles to follow the convention used in [MapCompose](https://github.com/p-lr/MapCompose)

## Installation

1. Clone the repository:

    ```bash
    git clone git@github.com:feczkob/osm-tile-manager.git
    ```

2. Navigate to the project directory:

    ```bash
    cd osm-tile-manager
    ```

3. Create a `.env` file in the root of the project and set the following parameters. 
Replace the values with the appropriate path, coordinates of the desired area and zoom level(s). 
See `.env.example` for an example.

    ```plaintext
    TILES_PATH=/your/path/to/the/tiles/folder
    TOP_LEFT_LAT=47.5189
    TOP_LEFT_LON=19.0129
    BOTTOM_RIGHT_LAT=47.4724
    BOTTOM_RIGHT_LON=19.0829
    MIN_ZOOM=13
    MAX_ZOOM=14
    ```

4. Build the project:

    ```bash
    ./gradlew clean build
    ```
   
## Usage

### Tile Download

To download the tiles, 
1. execute the following command:

    ```bash
    ./gradlew fetchTiles
    ```

    or
2. run the `main()` method in the `GenerateTiles.kt` file.

Verify that the tiles have been downloaded in the specified directory.
The fetched tiles cover the specified area (given in the `.env` file), and the same area is downloaded on all zoom levels.
The coordinates of the specified area and the one being fetched are usually different.
Read the `README.md` file next to the tiles for more information.

### Tile Renaming

To rename the downloaded tiles, 
1. execute the following command:

    ```bash
    ./gradlew renameTiles
    ```

    or
2. run the `main()` method in the `RenameTiles.kt` file.

Verify that the previously downloaded tiles have been renamed according to the specified convention.


   
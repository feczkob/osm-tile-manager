# OSM Tile Manager

OSM Tile Manager is a Kotlin application that allows you to download map tiles from a specified area and range of zoom levels. 
The downloaded tiles follow the `/zoom/x/y` convention, making them compatible with various mapping libraries and applications (e.g. [MapCompose](https://github.com/p-lr/MapCompose)).

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
Replace the values with the appropriate paths and coordinates for your desired area. 
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

To download the tiles, run the `main()` method in the `GenerateTiles.kt` file. 
Verify that the tiles have been downloaded in the specified directory.
Read the `README.md` file next to the tiles for more information.

### Tile Renaming

To rename the downloaded tiles, run the `main()` method in the `RenameTiles.kt` file.
Verify that the previously downloaded tiles have been renamed according to the specified convention.


   
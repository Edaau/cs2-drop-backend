# CS2 Drop Backend

## Overview

The **CS2 Drop Backend** is a Java 21 application built with the Spring Boot framework.  
Its primary goal is to simulate the opening of loot boxes, similar to those found in games like *Counter-Strike 2 (CS2)*, providing an API to manage loot boxes and their associated skins.

Currently, the project consists only of the backend, but there are future plans to develop a frontend that will allow users to interact with the system more intuitively.

## Main Features

- **Loot Box Management**: Create, update, retrieve, and delete loot boxes.  
- **Skin Management**: Associate skins with loot boxes, including defining drop probabilities.  
- **Loot Box Opening Simulation**: Simulate the opening of a loot box and return a skin based on predefined probabilities.  

## Project Structure

The project follows the **Model-View-Controller (MVC)** architecture, although it currently lacks a view layer.

src/main/java/com/cs2drop/ ├── controller/ # Handles HTTP requests ├── entity/ # Defines domain entities (Lootbox, Skin) ├── repository/ # Interfaces for database interaction ├── service/ # Contains business logic

## API Endpoints

The API provides the following endpoints to interact with the system:

### Loot Boxes

- **`GET /api/lootboxes`** → Retrieves a list of all available loot boxes.  
- **`GET /api/lootboxes/{id}`** → Retrieves details of a specific loot box by ID.  
- **`POST /api/lootboxes`** → Creates a new loot box with the provided data.  
- **`PUT /api/lootboxes/{id}`** → Updates an existing loot box by ID.  
- **`DELETE /api/lootboxes/{id}`** → Deletes the specified loot box.  

### Skins

- **`POST /api/lootboxes/{lootboxId}/skins`** → Adds a new skin to the specified loot box.  
- **`GET /api/lootboxes/{lootboxId}/skins`** → Retrieves all skins associated with a loot box.  
- **`DELETE /api/lootboxes/{lootboxId}/skins/{skinId}`** → Removes a skin from the specified loot box.  

### Loot Box Opening

- **`POST /api/lootboxes/{id}/open`** → Simulates opening a loot box and returns a skin based on predefined probabilities.  

## API Usage Example

To open a loot box with **ID 1**, send a `POST` request to:

/api/lootboxes/1/open

The response will contain the skin obtained from the simulation.

## Future Plans

There are plans to develop a **frontend** to complement this backend, providing a user-friendly graphical interface to interact with the loot box and skin system.

## Contribution

Contributions are welcome! Feel free to open issues or submit pull requests to improve the project.

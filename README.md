# pockeApp

![изображение](https://user-images.githubusercontent.com/57913130/135768993-e6bd2ffb-367d-4b18-b42f-8edd2aceed87.png)

![изображение](https://user-images.githubusercontent.com/57913130/135769012-93f8fbe2-277a-4ae0-b2d3-50c672fae6d2.png)

A knowledge base app for the game Pokémon. Implemented the page loading list of Pokémon in the list, it is possible to use filters (sorting) of attack, defense, health. When you click on an item in the list, you can open a page with the character, with its detailed characteristics.

MVVM is used as the design template, components are divided into layers: domain, data, presentation, ui.
Retrofit2 is used to communicate with the network, and koin is used to inject dependencies.
The coil and glide were used to load the images. Coroutines were used for the asynchronous code.

complete demonstration of the application:
https://user-images.githubusercontent.com/57913130/135769403-2bb0581b-97db-46a1-98f8-14369b897531.mp4

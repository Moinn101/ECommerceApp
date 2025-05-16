E-Commerce Product Details App

Overview


This Android application fetches and displays product details for an e-commerce platform, built as an assignment for Inovant Solutions.
Developed using Kotlin with MVVM architecture, it uses Retrofit to parse data from a provided web service and presents a clean, 
responsive UI that matches the provided design mockups, ensuring a seamless user experience.


Features


Displays product name, SKU, price, brand, and description.

Image carousel with ViewPager2 and dot indicators, maintaining image ratio.

Quantity selector with increment/decrement buttons.

Share product URL via top/bottom bar buttons.

Shows API error messages via Toast.

MVVM with Repository Pattern.

Retrofit for API parsing.

Kotlin for development.


How to Run


Clone the repository.

Open in Android Studio.

Ensure dependencies are in build.gradle.

Sync and run on emulator/device (min SDK 21).

Fetches data from:
https://klinq.com/rest/V1/productdetails/6701/253620?lang=en&store=KWD



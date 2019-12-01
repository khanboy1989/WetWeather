# The WetWeather Application


Application is built to by using Google's suggested architecture which is MVVM.

Main Purpose of the application:

By using Google places auto complete api the user selects a city.

Depending on selected city the openweathermap.org api is called to get current weather.

The application retrieves the next following three days weather information with time and date.

The following libraries are used:

    => ViewModel
    => Lifecycle
    => Navigation components
    => RxJava
    => Retrofit2
    => Dagger2
    => Mockito
    => Google places api
    => Glide

ListViewModel is responsible to perform indicates requests above.

Application contains two fragments. First fragment is called WeatherListFragment.
WeatherListFragment includes SearchView where user can search the city in case of his interest.

After search the the ListView brings the approximately three days data with dates and hours. The openweathermap api
does not provide daily forecast. It needs to be paid So, the application is consuming hourly and daily weather forecast.

The second fragment is called WeatherDetailsFragment where it displays the details of selected item from the list.

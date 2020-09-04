@objects
    loginButton  xpath    //a[@tracking-label = 'Header-Giris']
    brand        css      #brand

= Home Page Test =
    @on *
        loginButton:
            image file images/loginButton.png, error 3%
        brand:
            image file images/brand.png, error 3%

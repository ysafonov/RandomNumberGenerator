# Random number generator
![CI status](https://img.shields.io/badge/build-passing-brightgreen.svg)

There is an open-sourced Java project which was created as a part of the subject Cryptography in informatics ([MKRI](https://www.vutbr.cz/en/students/courses/detail/133999)) at The Faculty of Electrical Engineering, [Brno University of Technology](https://www.vutbr.cz/en/) (BUT). This project represents the application for generating random numbers which are widely used in modern cryptography.

## Project description

This section provides information about the application structure, its use cases and uml class diagrams.
See [project's  wiki description](https://github.com/ysafonov/RandomNumberGenerator/wiki) for more details.

## Authors

* **Yehor Safonov** - *programming, wiki documentation, git management, graphical user interface;*
* **Pavel Mazánek** - *programming, RNG modules, documentation, research;*
* **David Karger** - *programming, testing, RNG modules;*
* **David Pecl** - *RNG modules architecture, documentation, research.*

## Quick start

1) The first step is to clone the git repository:
```
git clone https://github.com/ysafonov/RandomNumberGenerator.git
```
2) Then build a project and run it via Main.java class.

## Application usage

For detailed information see a wiki page [Application usage](https://github.com/ysafonov/RandomNumberGenerator/wiki/Application-usage)

#### Window contining main setting of the random number generator, where a user is able to change and combine different techniques:
![Class diagram](https://github.com/ysafonov/RandomNumberGenerator/blob/master/Diagrams/config.png)

#### A user is able to compare his random nauber generator with the external ones and define which approach gives a better result:
![Class diagram](https://github.com/ysafonov/RandomNumberGenerator/blob/master/Diagrams/external-summary.png)

## License

This project is licensed under the GNU LGPL 3.0 License - see the [LGPL License](https://www.gnu.org/licenses/lgpl-3.0.en.html) for more details.

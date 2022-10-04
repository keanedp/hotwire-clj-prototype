# hotwire-clj

Demo site using Hotwire Turbo and Clojure.

## Installation

Install Leiningen, Clojure and Node

    $ bew install leiningen clojure node

#### Install NPM dependencies for building static assets

    $ npm install

#### Migrations

Initialize database:

    $ lein migratus migrate

## Usage

#### Static Assets

Watch and build Javascript and CSS static assets:

    $ make watch-assets

#### Dev Server

Run dev server:

    $ make dev


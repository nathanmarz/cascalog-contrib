# cascalog-contrib--template

A Leiningen template for generating [Cascalog-Contrib](https://github.com/nathanmarz/cascalog-contrib) modules.

## Usage

Run the following commands to install `cascalog-contrib-template` and create a new, custom koan project:

    lein plugin install lein-newnew 0.1.2
    lein plugin install cascalog-contrib-template 0.1.0
    lein new cascalog-contrib <module-name>
    mv <module-name> cascalog.<module-name>  # to keep the naming convention proper!
    cd cascalog.<module-name>

## Customizing your module

Generated modules come with a single test and a module namespace, located at `cascalog.<module-name>.clj`.

All new projects will require the following steps:

- add custom dependencies to `project.clj`. (Clojure defaults to 1.3.0, but feel free to roll back to 1.2.1 if you feel strongly about it..)
- Customize `README.md`

And that's it!

### License

The use and distribution terms for this software are covered by the
Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
which can be found in the file epl-v10.html at the root of this distribution.
By using this software in any fashion, you are agreeing to be bound by
the terms of this license.

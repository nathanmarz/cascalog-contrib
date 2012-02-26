# Lein-Cascalog

A Leiningen plugin for interacting with the [Cascalog](https://github.com/nathanmarz/cascalog-contrib) MapReduce DSL.

## Usage

Run the following commands to install `lein-cascalog` and create a new Cascalog project:

    lein plugin install lein-newnew 0.1.2
    lein plugin install lein-cascalog 0.1.0

## Creating a new Cascalog Project

With lein-cascalog, creating a new cascalog project is as simple as:

    lein new cascalog <project-name>
    cd <project-name>

Add your customizations to `project.clj`, run `lein deps` and you'll be good to go.

## Cascalog-Contrib Modules

lein-cascalog also contains a template for generating modules for inclusion in [Cascalog-Contrib](https://github.com/nathanmarz/cascalog-contrib). Follow the instructions above to install `lein-cascalog`, then fork and clone [Cascalog-Contrib](https://github.com/nathanmarz/cascalog-contrib). Inside of the project, run the following:

    lein new cascalog-contrib <module-name>
    mv <module-name> cascalog.<module-name>  # to keep the naming convention proper!
    cd cascalog.<module-name>

### Customizing your module

Generated modules come with a single test and a module namespace, located at `cascalog.<module-name>.clj`.

All new projects will require the following steps:

- add custom dependencies to `project.clj`.
- Customize `README.md`

And that's it!

## License

The use and distribution terms for this software are covered by the
Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
which can be found in the file epl-v10.html at the root of this distribution.
By using this software in any fashion, you are agreeing to be bound by
the terms of this license.

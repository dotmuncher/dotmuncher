#!/bin/bash


cat widget.html | image-nuggets \
                            --from=html \
                            --to=png \
                            --optipng-level=2 \
                            --dest-prefix=/Users/a/repos/dotmuncher/static/images/ \
                            widget/background.png


cat board-edit.html | image-nuggets \
                            --from=html \
                            --to=png \
                            --optipng-level=2 \
                            --dest-prefix=/Users/a/repos/dotmuncher/static/images/ \
                            board-edit/save.png \
                            board-edit/save_hover.png \
                            board-edit/save_active.png \
                            board-edit/bounds.png \
                            board-edit/bounds_hover.png \
                            board-edit/bounds_active.png \
                            board-edit/path.png \
                            board-edit/path_hover.png \
                            board-edit/path_active.png \
                            board-edit/base.png \
                            board-edit/base_hover.png \
                            board-edit/base_active.png \
                            board-edit/pellets.png \
                            board-edit/pellets_hover.png \
                            board-edit/pellets_active.png

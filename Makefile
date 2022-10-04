.PHONY: dev build-assets watch-assets

dev:
	@echo "Running development environment..."
	lein run

build-assets:
	@echo "Building static asset files"
	npm run build-assets

watch-assets:
	@echo "Watching for static asset file changes"
	npm run watch-assets

build-minimized-assets:
	@echo "Building minimized assets"
	npm run build-minimized-assets

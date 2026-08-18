# Jewellery Business Website — Project Specification

## 1. Project Overview

Build a professional, production-quality website for a small jewellery business.

The website is primarily a **jewellery catalogue and customer enquiry platform**, not a traditional online-payment e-commerce store.

Customers should be able to browse jewellery products and contact the business through WhatsApp or Instagram to purchase/enquire.

The website should be:

* Professional
* Secure
* Responsive
* Mobile-first
* Fast
* Accessible
* SEO-friendly
* Maintainable
* Easy for a non-technical business owner to manage
* Deployable using free-tier infrastructure where reasonably possible

The business owner should be able to manage the catalogue through a protected admin dashboard.

I have knowledge of Spring Boot backend development but limited frontend knowledge. The frontend will be built with React, and the coding agent should handle most implementation while explaining important architectural decisions when requested.

---

# 2. Business Model

The website is a:

**Jewellery Catalogue + Customer Enquiry Website**

It is NOT a traditional online-payment e-commerce website.

The customer flow is:

Home
→ Shop
→ Category/Search
→ Product Details
→ Enquire on WhatsApp
→ WhatsApp conversation with business owner

Instagram should also be available as a secondary contact/discovery channel.

The business owner will manually confirm:

* Availability
* Final price if necessary
* Delivery
* Payment
* Order details

The website must NOT process payments.

---

# 3. V1 Scope

## In Scope

* Professional jewellery catalogue
* Responsive React frontend
* Spring Boot REST backend
* PostgreSQL database
* Product management
* Category management
* Product image management
* Product availability
* Product search
* Category filtering
* Featured products
* Product detail pages
* WhatsApp product enquiry
* Instagram integration
* Business/about/contact information
* Admin dashboard
* Admin Google OAuth login
* SEO basics
* Accessibility
* Security
* Production configuration
* Deployment
* Documentation
* Testing

## Out of Scope

Do NOT implement these in V1:

* Customer accounts
* Customer Google login
* Customer registration
* Shopping cart
* Checkout
* Online payments
* Razorpay/payment gateway
* Card payments
* UPI payment processing
* Automated orders
* Shipping calculation
* Automated refunds
* Customer payment history
* Customer CRM
* AI chatbot
* Recommendation engine
* Microservices
* Kubernetes
* Redis
* Kafka
* Elasticsearch
* Complex analytics infrastructure

Do not implement out-of-scope functionality unless explicitly requested later.

---

# 4. Technology Stack

## Backend

Use:

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* Google OAuth 2.0 / OpenID Connect for ADMIN authentication
* PostgreSQL
* Bean Validation
* Maven
* REST APIs
* DTOs
* Global exception handling
* Appropriate logging
* Flyway for database migrations

Use currently supported stable versions.

Do not introduce unnecessary dependencies.

## Frontend

Use:

* React
* Vite
* Prefer TypeScript if it does not add unnecessary complexity
* React Router
* Lightweight styling approach
* Responsive/mobile-first design

The frontend communicates with the Spring Boot REST API.

Business logic must remain on the backend.

## Database

Use PostgreSQL.

Use proper relational modelling, constraints, indexes, and migrations.

Do not store images inside PostgreSQL.

---

# 5. Free-First Requirement

The application must be designed to operate using free/open-source technologies and reasonable free tiers wherever possible.

The target is:

**₹0 mandatory recurring infrastructure cost for V1.**

Do not introduce paid infrastructure unless there is no reasonable free alternative.

Do not compromise:

* Security
* Data integrity
* Maintainability
* Reliability

simply to avoid a small cost.

Before choosing external infrastructure, verify its CURRENT free-tier limitations.

Do not rely on outdated pricing or service information.

A custom domain is optional and may be added later.

The website should remain functional using the free provider domain if no custom domain is purchased.

---

# 6. Project Architecture

Use a modular monolith.

Do NOT use microservices.

Recommended architecture:

React/Vite frontend
↓
Spring Boot REST API
↓
PostgreSQL
↓
Object/file storage for product images

Admin authentication:

Google OAuth
↓
Spring Security
↓
ADMIN authorization

The backend owns all important business rules.

The frontend must never be treated as a trusted security boundary.

---

# 7. Admin Authentication

Only the business owner/admin needs authentication in V1.

Use Google OAuth/OpenID Connect.

Do NOT build username/password authentication unless there is a strong technical reason.

Only explicitly configured administrator Google accounts/emails may access the admin area.

The administrator identity must be configurable through environment variables.

Example:

ADMIN_EMAIL

Do not hardcode the actual administrator email into source code.

Every admin API endpoint must enforce authorization server-side.

Hiding an admin button in React is NOT sufficient security.

---

# 8. Public Website

The public website must require no login.

Customers should immediately be able to:

* Browse products
* Search
* Filter by category
* View product details
* View availability
* Contact the business

The website should be optimized for mobile because many customers may arrive through Instagram or WhatsApp.

---

# 9. Product Model

Products should support:

* ID
* SKU/product code where appropriate
* Name
* Description
* Price
* Category
* Availability
* Active/inactive state
* Featured state
* Created timestamp
* Updated timestamp

Only add additional fields when they provide genuine business value.

Availability should support at least:

* AVAILABLE
* OUT_OF_STOCK
* HIDDEN

A hidden product must not appear publicly.

An out-of-stock product must not be presented as available.

---

# 10. Product Images

Product images must be stored outside the Spring Boot server filesystem.

Use an appropriate free-tier object/file storage solution.

The database stores image metadata/URLs.

Support:

* Multiple images per product
* Image ordering
* Main/primary product image
* Image deletion/replacement
* Appropriate alt text

Optimize images for web usage.

Do not unnecessarily serve huge original photographs to mobile customers.

Use lazy loading where appropriate.

---

# 11. Categories

Products can belong to categories.

Possible categories may include:

* Earrings
* Necklaces
* Bracelets
* Rings
* Sets
* New Arrivals

These are examples only. Do not hardcode them as mandatory categories.

Categories must be manageable through the admin dashboard.

Products should be filterable by category.

---

# 12. Search

Implement useful product search.

Search should support product names and other appropriate searchable fields.

Do not build Elasticsearch.

Use PostgreSQL/database capabilities.

Search should handle:

* Empty search
* No results
* Partial matches
* Case differences

Provide useful empty states.

---

# 13. WhatsApp Integration

WhatsApp is the primary conversion mechanism.

Every product detail page should have a prominent:

**Enquire on WhatsApp**

button.

Where appropriate, product cards may also have a smaller WhatsApp CTA.

Clicking the button should open WhatsApp's official click-to-chat mechanism with a pre-filled message.

The message should include useful information such as:

* Product name
* Product ID/SKU if available

Example:

"Hi, I'm interested in [PRODUCT NAME] (Product ID: [ID]). Could you please tell me if it is available and provide the details?"

The WhatsApp number must be configurable.

Do NOT hardcode it throughout the application.

For the frontend, use appropriate environment configuration, for example:

VITE_WHATSAPP_NUMBER

Do not expose unrelated private information.

The implementation should work properly on mobile and desktop.

---

# 14. Instagram

Instagram is a secondary business channel.

Provide appropriate links such as:

* Instagram profile
* Follow us on Instagram
* See more on Instagram

The Instagram URL must be configurable.

Example:

VITE_INSTAGRAM_URL

Do not hardcode the URL throughout multiple components.

---

# 15. Homepage

The homepage should be designed around jewellery discovery and conversion.

Possible sections:

* Header/navigation
* Logo/brand
* Hero section
* Featured products
* Categories
* New arrivals
* About the business
* Why choose us/trust section if appropriate
* Instagram section
* WhatsApp CTA
* Contact information
* Footer

Do not add sections simply to make the homepage longer.

Every section should have a purpose.

Product photography should remain the visual focus.

---

# 16. Frontend Design

I will provide the business logo and colour scheme separately.

When provided:

* Use the logo consistently.
* Derive the visual system from the provided colours.
* Maintain good contrast.
* Maintain consistent spacing.
* Maintain consistent typography.
* Make buttons and CTAs visually coherent.
* Make product cards consistent.
* Keep the design premium and clean.

Target design characteristics:

* Elegant
* Premium
* Modern
* Clean
* Product-focused
* Trustworthy
* Mobile-friendly

Avoid:

* Excessive gradients
* Excessive glassmorphism
* Unnecessary animations
* Clutter
* Generic AI-generated-looking layouts
* Excessive rounded cards everywhere
* Huge empty spaces
* Distracting effects

Animations should be subtle and purposeful.

---

# 17. Required Pages

## Public

* Home
* Shop
* Product Details
* Search Results
* Category Results
* About
* Contact
* Privacy Policy
* Terms & Conditions
* Shipping Information
* Return/Refund Information
* 404 page

## Admin

* Admin Login
* Dashboard
* Products
* Add Product
* Edit Product
* Categories
* Product Images
* Availability Management

Do not create unnecessary pages.

---

# 18. Admin Dashboard

The admin should be able to:

* Create products
* Edit products
* Deactivate products
* Delete products where safe
* Upload product images
* Reorder product images
* Set primary image
* Create categories
* Edit categories
* Delete categories where safe
* Set product price
* Set availability
* Mark products as featured
* Search products
* Filter products
* View basic catalogue statistics

The admin interface should prioritize ease of use.

The business owner should not need technical knowledge to manage the catalogue after deployment.

---

# 19. REST API

Create clean REST endpoints.

Use:

* DTOs
* Validation
* Appropriate HTTP status codes
* Pagination where appropriate
* Filtering
* Search
* Global exception handling
* Consistent API error responses

Do not expose JPA entities directly.

Do not expose database internals.

Do not put business rules exclusively in controllers.

Use appropriate service-layer separation.

---

# 20. Security

Treat the application as a real public website.

Implement:

* Spring Security
* Google OAuth for admin
* Role-based authorization
* Server-side authorization
* Input validation
* Secure CORS
* Appropriate HTTP security headers
* Safe error responses
* No stack traces in production responses
* No secrets in Git
* Environment variables for secrets
* Secure production configuration
* HTTPS in production
* Proper authentication failure handling

Never trust:

* Frontend admin status
* Frontend prices
* Frontend availability
* Arbitrary IDs
* Arbitrary URLs
* Arbitrary uploaded files

For file uploads:

* Validate file type.
* Validate file size.
* Reject inappropriate files.
* Use safe storage.
* Avoid executing uploaded content.

Do not store unnecessary personal information.

---

# 21. Performance

Optimize for a small business with potentially low traffic but mobile users.

Use:

* Compressed/appropriately sized images
* Lazy image loading
* Pagination
* Efficient database queries
* Appropriate indexes
* Avoidance of N+1 queries
* Efficient React rendering
* Sensible caching only where useful
* Minimal unnecessary API calls

Do not prematurely optimize.

Do not introduce Redis or other infrastructure unless a concrete requirement emerges.

---

# 22. SEO

Implement sensible baseline SEO.

Include:

* Meaningful page titles
* Meta descriptions
* Clean URLs
* Product metadata
* Open Graph metadata where appropriate
* robots.txt
* sitemap where practical
* Semantic HTML
* Product structured data/schema markup where appropriate

Product pages should be shareable.

A product link shared on WhatsApp or Instagram should have useful metadata where possible.

---

# 23. Accessibility

Implement:

* Semantic HTML
* Alt text
* Keyboard navigation
* Accessible forms
* Proper labels
* Visible focus states
* Sufficient contrast
* Accessible buttons
* Meaningful error messages

Do not sacrifice accessibility for visual effects.

---

# 24. Error and Empty States

Every major frontend operation should have:

* Loading state
* Success state where relevant
* Empty state
* Error state

Examples:

* No products
* No search results
* Product unavailable
* Backend unavailable
* Image upload failed
* Invalid admin action

Never leave the user staring at a blank screen.

---

# 25. Configuration

Use environment variables for:

* Database credentials
* Google OAuth client ID
* Google OAuth client secret
* Admin email
* Storage credentials
* WhatsApp number
* Instagram URL
* Frontend API URL
* Other secrets

Provide:

.env.example

Never commit:

.env

or actual secrets.

---

# 26. Database

Use Flyway migrations.

Do not rely on:

spring.jpa.hibernate.ddl-auto=create

for production.

Use proper:

* Foreign keys
* Indexes
* Unique constraints
* Timestamps
* Nullable constraints
* Relationship mappings

Avoid dangerous cascade behaviour.

---

# 27. Testing

Do not consider the application complete merely because it compiles.

Test important functionality.

Backend:

* Product creation
* Product retrieval
* Product update
* Product deletion/deactivation
* Category functionality
* Search
* Filtering
* Validation
* Admin authorization
* Unauthorized access
* Image-related endpoints where appropriate

Frontend:

* Navigation
* Product browsing
* Search
* Category filtering
* Product detail
* WhatsApp CTA
* Responsive behaviour
* Admin flows

At milestone completion:

* Build the backend.
* Build the frontend.
* Run relevant automated tests.
* Inspect for obvious runtime errors.

Do not repeatedly run expensive full test suites for trivial unrelated changes.

---

# 28. Documentation

Maintain:

README.md

and:

DEVELOPMENT_STATUS.md

README should contain:

* Project overview
* Architecture
* Technology stack
* Setup
* Environment variables
* Database setup
* Google OAuth setup
* Image storage setup
* Local development
* Frontend development
* Backend development
* Testing
* Production build
* Deployment
* Troubleshooting

DEVELOPMENT_STATUS.md should remain concise.

It should contain:

* Completed phases
* Current phase
* Important architectural decisions
* Known issues
* Next recommended task

This file exists partly so future Codex sessions or other AI coding agents can quickly regain project context.

---

# 29. Git

Use Git throughout development.

Create meaningful commits at milestones.

Never commit:

* Secrets
* .env
* Credentials
* Generated private keys
* Unnecessary build artifacts

Maintain an appropriate .gitignore.

Before milestone commits:

* Run relevant tests.
* Build.
* Inspect git diff.
* Verify no secrets were accidentally added.

---

# 30. Deployment

The final application must be publicly deployable.

Use free-tier infrastructure where reasonably possible.

Do not choose infrastructure based on old information.

Before deployment, verify current service limitations.

The deployment architecture should support:

Frontend
↓
Spring Boot API
↓
PostgreSQL

and:

Spring Boot
↓
Object storage

The application must not depend on local filesystem persistence.

Ensure:

* Production environment variables
* Production CORS
* Google OAuth redirect URLs
* Frontend API URL
* Database connection
* Image storage
* HTTPS
* Health checks
* Production build
* Startup configuration

all work correctly.

Explain any unavoidable free-tier limitations.

---

# 31. Credit/Token Efficiency

I want to build this using AI coding agents while using a reasonable amount of credits.

Do not sacrifice correctness or security to save tokens.

However:

* Inspect only relevant files before making a change.
* Do not repeatedly rediscover the entire repository.
* Use DEVELOPMENT_STATUS.md as project memory.
* Do not rewrite working code.
* Do not introduce unnecessary dependencies.
* Do not perform speculative refactors.
* Do not regenerate unchanged files.
* Keep tasks focused.
* Prefer one coherent feature at a time.
* Run targeted tests during development.
* Run broader verification at milestones.
* Summarize changes instead of dumping entire files.
* State important decisions briefly.
* If an unrelated problem is discovered, report it rather than automatically expanding scope.
* Reuse existing implementations where appropriate.

The objective is:

**Maximum useful progress per AI credit without reducing engineering quality.**

---

# 32. Coding Style

Prefer:

* Readable code
* Conventional Spring Boot architecture
* Meaningful names
* Small cohesive methods
* Clear DTOs
* Clear services
* Predictable React components
* Reusable UI components where genuinely useful
* Minimal dependencies

Avoid:

* Giant classes
* Giant React components
* Duplicated logic
* Unnecessary abstraction layers
* Magic numbers
* Hardcoded business configuration
* Dead code
* Commented-out abandoned implementations

---

# 33. AI Coding Rules

Do not blindly follow a request if it creates a security, performance, or architectural problem.

If a requested implementation is problematic:

1. Explain the issue briefly.
2. Recommend the safer/simpler solution.
3. Implement the better solution unless my decision is explicit and necessary.

Do not invent library APIs.

Do not assume third-party services work a certain way if current documentation is available.

For external services, verify current documentation when necessary.

---

# 34. Development Workflow

Build the application incrementally.

Use these phases:

## PHASE 0

Repository and environment inspection

## PHASE 1

Backend project foundation

## PHASE 2

Database + product/category domain

## PHASE 3

Admin authentication + authorization

## PHASE 4

Product/category REST APIs

## PHASE 5

Image storage and image management

## PHASE 6

React frontend foundation

## PHASE 7

Catalogue UI + product pages

## PHASE 8

Search/filtering + responsive polish

## PHASE 9

WhatsApp + Instagram conversion flow

## PHASE 10

Admin dashboard

## PHASE 11

Security hardening

## PHASE 12

SEO + accessibility

## PHASE 13

Testing and bug fixing

## PHASE 14

Production configuration

## PHASE 15

Deployment

## PHASE 16

Final end-to-end audit

Do not attempt to implement every phase in one operation.

---

# 35. Phase Completion Rules

At the end of every phase:

1. Build the relevant part of the application.
2. Run appropriate tests.
3. Fix errors caused by the current phase.
4. Review the Git diff.
5. Check that no secrets were introduced.
6. Update DEVELOPMENT_STATUS.md.
7. Summarize what was implemented.
8. Identify the next phase.
9. Create a meaningful Git commit.

Do not proceed to a large new phase if the current phase is fundamentally broken.

---

# 36. Definition of Done

Do not claim V1 is finished until:

* Backend builds successfully.
* Frontend builds successfully.
* Database migrations work.
* Admin authentication works.
* Admin authorization works.
* Products can be managed.
* Categories can be managed.
* Images can be managed.
* Public catalogue works.
* Search works.
* Filtering works.
* Product pages work.
* WhatsApp links work.
* Instagram links work.
* Mobile layout works.
* Major error states work.
* Security checks have been performed.
* Tests pass.
* Production configuration exists.
* Deployment has been tested.
* README is complete.
* No secrets are committed.
* Final Git diff has been reviewed.

The final result should be a genuinely usable small-business jewellery catalogue, not merely a code-generation demonstration.

-- =====================================================
-- SQL Script to Create Sequences for Tour Management System
-- Run this script in your MySQL database before starting the application
-- =====================================================

USE quanlybantour;

-- Drop existing sequences if they exist (optional - for clean setup)
DROP TABLE IF EXISTS account_seq;
DROP TABLE IF EXISTS tour_seq;
DROP TABLE IF EXISTS guide_seq;
DROP TABLE IF EXISTS accommodation_seq;
DROP TABLE IF EXISTS destination_seq;
DROP TABLE IF EXISTS vehicle_seq;
DROP TABLE IF EXISTS promotion_seq;
DROP TABLE IF EXISTS customer_seq;
DROP TABLE IF EXISTS invoice_seq;

-- =====================================================
-- Create Sequence Tables
-- =====================================================

-- Sequence for Accounts (ACCT000001, ACCT000002, ...)
CREATE TABLE account_seq (
    next_val BIGINT NOT NULL
);
INSERT INTO account_seq VALUES (1);

-- Sequence for Tours (TOUR000001, TOUR000002, ...)
CREATE TABLE tour_seq (
    next_val BIGINT NOT NULL
);
INSERT INTO tour_seq VALUES (1);

-- Sequence for Tour Guides (GUID000001, GUID000002, ...)
CREATE TABLE guide_seq (
    next_val BIGINT NOT NULL
);
INSERT INTO guide_seq VALUES (1);

-- Sequence for Accommodations (ACCO000001, ACCO000002, ...)
CREATE TABLE accommodation_seq (
    next_val BIGINT NOT NULL
);
INSERT INTO accommodation_seq VALUES (1);

-- Sequence for Tourist Destinations (DEST000001, DEST000002, ...)
CREATE TABLE destination_seq (
    next_val BIGINT NOT NULL
);
INSERT INTO destination_seq VALUES (1);

-- Sequence for Travel Vehicles (VEHI000001, VEHI000002, ...)
CREATE TABLE vehicle_seq (
    next_val BIGINT NOT NULL
);
INSERT INTO vehicle_seq VALUES (1);

-- Sequence for Promotions (PROM000001, PROM000002, ...)
CREATE TABLE promotion_seq (
    next_val BIGINT NOT NULL
);
INSERT INTO promotion_seq VALUES (1);

-- Sequence for Customers (if needed)
CREATE TABLE customer_seq (
    next_val BIGINT NOT NULL
);
INSERT INTO customer_seq VALUES (1);


-- Sequence for Invoices (if needed)
CREATE TABLE invoice_seq (
    next_val BIGINT NOT NULL
);
INSERT INTO invoice_seq VALUES (1);

-- =====================================================
-- Verify sequences were created
-- =====================================================
SHOW TABLES LIKE '%_seq';

-- =====================================================
-- Check sequence values
-- =====================================================
SELECT 'account_seq' as sequence_name, next_val FROM account_seq
UNION ALL
SELECT 'tour_seq', next_val FROM tour_seq
UNION ALL
SELECT 'guide_seq', next_val FROM guide_seq
UNION ALL
SELECT 'accommodation_seq', next_val FROM accommodation_seq
UNION ALL
SELECT 'destination_seq', next_val FROM destination_seq
UNION ALL
SELECT 'vehicle_seq', next_val FROM vehicle_seq
UNION ALL
SELECT 'promotion_seq', next_val FROM promotion_seq
UNION ALL
SELECT 'customer_seq', next_val FROM customer_seq
UNION ALL
SELECT 'invoice_seq', next_val FROM invoice_seq;

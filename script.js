const API_URL = "http://localhost:8080";


// ==================================================
// SECTION NAVIGATION
// ==================================================

function showSection(sectionId) {

    const sections =
        document.querySelectorAll(".section");

    sections.forEach(section => {

        section.classList.remove("active");

    });


    document
        .getElementById(sectionId)
        .classList.add("active");


    if (sectionId === "customers") {
        loadCustomers();
    }


    if (sectionId === "accounts") {
        loadAccounts();
    }

}



// ==================================================
// ADD CUSTOMER
// ==================================================

document
    .getElementById("customerForm")
    .addEventListener("submit", function(event) {

        event.preventDefault();


        const customer = {

            name:
                document.getElementById("customerName").value,

            email:
                document.getElementById("customerEmail").value,

            phone:
                document.getElementById("customerPhone").value,

            address:
                document.getElementById("customerAddress").value

        };


        fetch(API_URL + "/customers", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(customer)

        })

        .then(response => {

            if (!response.ok) {

                return response.json()
                    .then(error => {

                        throw new Error(error.message);

                    });

            }

            return response.json();

        })

        .then(data => {

            alert("Customer added successfully!");

            document
                .getElementById("customerForm")
                .reset();

            loadCustomers();

        })

        .catch(error => {

            alert(error.message);

        });

    });



// ==================================================
// GET ALL CUSTOMERS
// ==================================================

function loadCustomers() {

    fetch(API_URL + "/customers")

        .then(response => {

            if (!response.ok) {

                throw new Error(
                    "Failed to load customers"
                );

            }

            return response.json();

        })

        .then(customers => {

            const table =
                document.getElementById("customerTable");


            table.innerHTML = "";


            document.getElementById("customerCount")
                .innerText = customers.length;


            customers.forEach(customer => {

                const row =
                    document.createElement("tr");


                row.innerHTML = `

                    <td>${customer.id}</td>

                    <td>${customer.name}</td>

                    <td>${customer.email}</td>

                    <td>${customer.phone}</td>

                    <td>${customer.address}</td>

                    <td>

                        <button
                            onclick="deleteCustomer(${customer.id})">
                            Delete
                        </button>

                    </td>

                `;


                table.appendChild(row);

            });

        })

        .catch(error => {

            alert(error.message);

        });

}



// ==================================================
// DELETE CUSTOMER
// ==================================================

function deleteCustomer(customerId) {

    const confirmation =
        confirm("Are you sure you want to delete this customer?");


    if (!confirmation) {
        return;
    }


    fetch(API_URL + "/customers/" + customerId, {

        method: "DELETE"

    })

    .then(response => {

        if (!response.ok) {

            return response.json()
                .then(error => {

                    throw new Error(error.message);

                });

        }

        return response.text();

    })

    .then(message => {

        alert(message);

        loadCustomers();

    })

    .catch(error => {

        alert(error.message);

    });

}



// ==================================================
// CREATE ACCOUNT
// ==================================================

document
    .getElementById("accountForm")
    .addEventListener("submit", function(event) {

        event.preventDefault();


        const customerId =
            document.getElementById(
                "accountCustomerId"
            ).value;


        const account = {

            accountNumber:
                document.getElementById(
                    "accountNumber"
                ).value,

            accountType:
                document.getElementById(
                    "accountType"
                ).value

        };


        fetch(
            API_URL +
            "/accounts/customer/" +
            customerId,
            {

                method: "POST",

                headers: {

                    "Content-Type":
                        "application/json"

                },

                body:
                    JSON.stringify(account)

            }
        )

        .then(response => {

            if (!response.ok) {

                return response.json()
                    .then(error => {

                        throw new Error(
                            error.message
                        );

                    });

            }

            return response.json();

        })

        .then(data => {

            alert(
                "Account created successfully!"
            );


            document
                .getElementById("accountForm")
                .reset();


            loadAccounts();

        })

        .catch(error => {

            alert(error.message);

        });

    });



// ==================================================
// GET ALL ACCOUNTS
// ==================================================

function loadAccounts() {

    fetch(API_URL + "/accounts")

        .then(response => {

            if (!response.ok) {

                throw new Error(
                    "Failed to load accounts"
                );

            }

            return response.json();

        })

        .then(accounts => {

            const table =
                document.getElementById(
                    "accountTable"
                );


            table.innerHTML = "";


            document.getElementById(
                "accountCount"
            ).innerText = accounts.length;


            let totalBalance = 0;


            accounts.forEach(account => {

                totalBalance += account.balance;


                const row =
                    document.createElement("tr");


                row.innerHTML = `

                    <td>${account.id}</td>

                    <td>${account.accountNumber}</td>

                    <td>${account.accountType}</td>

                    <td>₹${account.balance}</td>

                    <td>${account.status}</td>

                    <td>

                        <button
                            onclick="blockAccount(${account.id})">
                            Block
                        </button>

                        <button
                            onclick="activateAccount(${account.id})">
                            Activate
                        </button>

                        <button
                            onclick="closeAccount(${account.id})">
                            Close
                        </button>

                    </td>

                `;


                table.appendChild(row);

            });


            document.getElementById(
                "totalBalance"
            ).innerText =
                "₹" + totalBalance;

        })

        .catch(error => {

            alert(error.message);

        });

}



// ==================================================
// BLOCK ACCOUNT
// ==================================================

function blockAccount(accountId) {

    fetch(
        API_URL +
        "/accounts/" +
        accountId +
        "/block",
        {

            method: "PUT"

        }
    )

    .then(response => {

        if (!response.ok) {

            return response.json()
                .then(error => {

                    throw new Error(
                        error.message
                    );

                });

        }

        return response.json();

    })

    .then(data => {

        alert("Account blocked successfully!");

        loadAccounts();

    })

    .catch(error => {

        alert(error.message);

    });

}



// ==================================================
// ACTIVATE ACCOUNT
// ==================================================

function activateAccount(accountId) {

    fetch(
        API_URL +
        "/accounts/" +
        accountId +
        "/activate",
        {

            method: "PUT"

        }
    )

    .then(response => {

        if (!response.ok) {

            return response.json()
                .then(error => {

                    throw new Error(
                        error.message
                    );

                });

        }

        return response.json();

    })

    .then(data => {

        alert(
            "Account activated successfully!"
        );

        loadAccounts();

    })

    .catch(error => {

        alert(error.message);

    });

}



// ==================================================
// CLOSE ACCOUNT
// ==================================================

function closeAccount(accountId) {

    const confirmation =
        confirm(
            "Are you sure you want to close this account?"
        );


    if (!confirmation) {
        return;
    }


    fetch(
        API_URL +
        "/accounts/" +
        accountId +
        "/close",
        {

            method: "PUT"

        }
    )

    .then(response => {

        if (!response.ok) {

            return response.json()
                .then(error => {

                    throw new Error(
                        error.message
                    );

                });

        }

        return response.json();

    })

    .then(data => {

        alert(
            "Account closed successfully!"
        );

        loadAccounts();

    })

    .catch(error => {

        alert(error.message);

    });

}



// ==================================================
// DEPOSIT
// ==================================================

function depositMoney() {

    const accountId =
        document.getElementById(
            "depositAccountId"
        ).value;


    const amount =
        document.getElementById(
            "depositAmount"
        ).value;


    fetch(
        API_URL +
        "/accounts/" +
        accountId +
        "/deposit?amount=" +
        amount,
        {

            method: "POST"

        }
    )

    .then(response => {

        if (!response.ok) {

            return response.json()
                .then(error => {

                    throw new Error(
                        error.message
                    );

                });

        }

        return response.json();

    })

    .then(data => {

        alert(
            "Money deposited successfully!"
        );

        loadAccounts();

    })

    .catch(error => {

        alert(error.message);

    });

}



// ==================================================
// WITHDRAW
// ==================================================

function withdrawMoney() {

    const accountId =
        document.getElementById(
            "withdrawAccountId"
        ).value;


    const amount =
        document.getElementById(
            "withdrawAmount"
        ).value;


    fetch(
        API_URL +
        "/accounts/" +
        accountId +
        "/withdraw?amount=" +
        amount,
        {

            method: "POST"

        }
    )

    .then(response => {

        if (!response.ok) {

            return response.json()
                .then(error => {

                    throw new Error(
                        error.message
                    );

                });

        }

        return response.json();

    })

    .then(data => {

        alert(
            "Money withdrawn successfully!"
        );

        loadAccounts();

    })

    .catch(error => {

        alert(error.message);

    });

}



// ==================================================
// TRANSFER
// ==================================================

function transferMoney() {

    const fromAccountId =
        Number(
            document.getElementById(
                "fromAccountId"
            ).value
        );


    const toAccountId =
        Number(
            document.getElementById(
                "toAccountId"
            ).value
        );


    const amount =
        Number(
            document.getElementById(
                "transferAmount"
            ).value
        );


    const transferRequest = {

        fromAccountId:
            fromAccountId,

        toAccountId:
            toAccountId,

        amount:
            amount

    };


    fetch(
        API_URL +
        "/accounts/transfer",
        {

            method: "POST",

            headers: {

                "Content-Type":
                    "application/json"

            },

            body:
                JSON.stringify(
                    transferRequest
                )

        }
    )

    .then(response => {

        if (!response.ok) {

            return response.json()
                .then(error => {

                    throw new Error(
                        error.message
                    );

                });

        }

        return response.text();

    })

    .then(message => {

        alert(
            "Transfer successful!"
        );

        loadAccounts();

    })

    .catch(error => {

        alert(error.message);

    });

}



// ==================================================
// BALANCE INQUIRY
// ==================================================

function checkBalance() {

    const accountId =
        document.getElementById(
            "balanceAccountId"
        ).value;


    fetch(
        API_URL +
        "/accounts/" +
        accountId +
        "/balance"
    )

    .then(response => {

        if (!response.ok) {

            return response.json()
                .then(error => {

                    throw new Error(
                        error.message
                    );

                });

        }

        return response.json();

    })

    .then(balance => {

        document.getElementById(
            "balanceResult"
        ).innerText =
            "Current Balance: ₹" + balance;

    })

    .catch(error => {

        alert(error.message);

    });

}



// ==================================================
// TRANSACTION HISTORY
// ==================================================

function loadTransactions() {

    const accountId =
        document.getElementById(
            "historyAccountId"
        ).value;


    fetch(
        API_URL +
        "/transactions/account/" +
        accountId
    )

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "Failed to load transactions"
            );

        }

        return response.json();

    })

    .then(transactions => {

        const table =
            document.getElementById(
                "transactionTable"
            );


        table.innerHTML = "";


        transactions.forEach(transaction => {

            const row =
                document.createElement("tr");


            row.innerHTML = `

                <td>${transaction.id}</td>

                <td>${transaction.transactionType}</td>

                <td>₹${transaction.amount}</td>

                <td>${transaction.transactionDate}</td>

                <td>
                    ${transaction.fromAccountId ?? "-"}
                </td>

                <td>
                    ${transaction.toAccountId ?? "-"}
                </td>

            `;


            table.appendChild(row);

        });

    })

    .catch(error => {

        alert(error.message);

    });

}